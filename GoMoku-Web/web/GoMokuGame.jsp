<%-- 
    Document   : GoMokuGame
    Created on : 21/08/2009, 15:01:30
    Author     : Slim
--%>

<%@page contentType='text/html' pageEncoding='UTF-8'%>
<%@page import="goMoku.Model.*, goMoku.Controller.*, goMoku.Controller.Web.*" %>

<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'
    'http://www.w3.org/TR/html4/loose.dtd'>
<HTML>
    <HEAD>
        <%--<script type="javascript" src="GoMokuGame.js"></script>--%>
        <Meta HTTP-Equiv='Content-Type' Content='Text/HTML; Charset=Windows-1255'>
        <TITLE>משחק 5 בשורה</TITLE>
        <link rel='stylesheet' type='text/css' href='GameStyle-Green.css' />
        <link rel='stylesheet' type='text/css' href='standart.css' />
    </HEAD>

    <BODY DIR=RTL>
        <script type="text/javascript" src="GoMokuGame.js"></script>


        <%!    GoMokuWebGame game;
    int size;
    int coltitle;
    GameBoard gameBoard;
    String columnName;
        %>
        <%
            // if someone reached the jsp directly without init
            Object objGame = session.getAttribute(goMoku.Controller.Web.GamePlay.ATT_GAME);
            if (objGame == null) {
                response.sendError(500, "No game found");
            } else {
                game = (GoMokuWebGame) (objGame);
                gameBoard = game.getGameBoard();
                size = gameBoard.getSize();
            }
        %>

        <%@include file="menu.jsp" %>

        <div style="text-align:center; direction:ltr; margin-bottom:10px; margin-top:10px;">
            GoMoku Game: <%= game.getGameType()%> (<img alt="1" src="player1.bmp"> vs <img alt="2" src="player2.bmp">)
        </div>
        <form id="moveForm" method="post" action="GamePlay"><input type="hidden" id="move" name="move"/></form>
        <div style="border:1px solid black; text-align:center; direction:rtl;" class="index">
            <table cellpadding='0' cellspacing='0' dir='ltr' align="center" class='board'>
                <thead>
                    <tr>
                        <th class='td_s'><font color='#00B1F7'>0</font></th>
                        <%
            // Create headers for the columns of the board
            for (coltitle = 0; coltitle < size; coltitle++) {
                columnName = String.format("%c", (char) ((int) (GameBoard.BOARD_START_COLUMN) + coltitle));
                        %>
                        <th class='td_s'><%=columnName%></th>

                        <%
            }
                        %>
                    </tr>
                </thead>
                <tbody>
                    <%
            // Create all the board lines
            for (int line = 1; line <= size; line++) {
                    %>
                    <tr>
                        <td class='td_s'><%=line%></td>
                        <%
                for (int col = 1; col <= size; col++) {
                    columnName = String.format("%c", (char) ((int) (GameBoard.BOARD_START_COLUMN) + col - 1));

                    // Check if theres a pawn and show it
                    PawnType currPawn = gameBoard.getPawnType(line, col);
                    if (currPawn == null) {
                        %>
                        <td class='td_<%= ((col + 1) % 2 + 1)%>' onclick="MakeMove('<%=columnName%>', '<%=line%>')" > </td>
                        <%
                    } else if (currPawn == PawnType.White) {
                        %>
                        <td class='td_<%= ((col + 1) % 2 + 1)%>'> <img alt="x" src="player1.bmp"/> </td>
                            <%
                    } else if (currPawn == PawnType.Black) {
                            %>
                        <td class='td_<%= ((col + 1) % 2 + 1)%>'> <img alt="0" src="player2.bmp"/> </td>
                            <%
                    }

                }
                            %>

                    </tr>
                    <%
            }
                    %>
                </tbody>
            </table>
        </div>

        <div style='background-color: #D7DFFF; border: 1px solid blue; text-align:center; vertical-align:bottom'>
            <table border='0' cellpadding='4' width='100%' style='border: 3px solid white;' valign='bottom'>
                <tbody>
                    <tr>
                        <td align="right" style="width:auto;">
                            <input type='button' value='Move' CLASS='BUTTON' onclick='MakeTextMove()'/>
                            <input type='text' id='moveText' style='width: 50px;' class='FIELD' dir='ltr'>
                        </td>
                        <td align="left" dir="ltr" style="width:33%;">
                            <%
                            // Check if the game is over
                              boolean gameOver = (session.getAttribute(GamePlay.ATT_VICTORY) != null);
                              if (!gameOver)
                              {
                                  String playerMark = game.getCurrPlayer().getName().compareTo("White") == 0 ? "player1.bmp" : "player2.bmp";
                              %>
                              Current player: <img  alt="<%= game.getCurrPlayer().getName() %>" src="<%= playerMark %>">
                              <%
                             }
                              else
                              {
                                  // Get the state and the winner
                                  boolean victory = ((Boolean)(session.getAttribute(GamePlay.ATT_VICTORY))).booleanValue();
                                  Player winner = (Player)(session.getAttribute(GamePlay.ATT_WINNER));

                                  // On victory - announce the winner
                                  // On a tie show game over
                                  if (victory)
                                  {
                                      String playerMark = winner.getName().compareTo("White") == 0 ? "player1.bmp" : "player2.bmp";
                                      %>
                                      <h2>The winner is: <img  alt="<%= winner.getName() %>" src="<%= playerMark %>"></h2>
                                      <%
                                  }
                                  else
                                  {
                                      %>
                                      <h2>Game Over with a tie!</h2>
                                      <%
                                  }
                              }
                            %>
                        </td>
                        <td dir='ltr' align="left" style="width:45%;">
                            <form id="newGameForm" Method='POST' action='GamePlay'>
                            <select name='GameType' id='GameType' class='SELECT' dir='ltr'>
                                <%
        // Get the possible game modes and put them in the combobox
        for (GoMokuGameType currType : GoMokuGameType.values()) {
            String typeName = currType.toString();
            String selected = currType == game.getGameType() ? "SELECTED" : "";
                                %>
                                <option value="<%=typeName%>" <%=selected%>><%=typeName%></option>
                                <%
        }
                                %>
                            </select>
                                <input type="hidden" name="newGame" id="newGame">
                                <input type='submit' value='NewGame' CLASS='BUTTON'/></td></tr>
                    </form>
                </tbody>
            </table>
        </div>
    </BODY>
</HTML>
