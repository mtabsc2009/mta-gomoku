/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


function MakeMove(columnName, rowNumber)
{
    document.getElementById("move").setAttribute("value", rowNumber+columnName);
    moveForm = document.getElementById("moveForm");
    moveForm.submit();
}


function MakeTextMove()
{
    textMove = document.getElementById("moveText");
    document.getElementById("move").setAttribute("value", textMove.value);
    moveForm = document.getElementById("moveForm");
    moveForm.submit();
}
