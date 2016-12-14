/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
setInterval(function () {
    var span = document.querySelector("#counter");
    var count = span.textContent * 1 - 1;
    span.textContent = count;
    if (count <= 0) {
        location.href = "/quizzes";
    }
}, 1000);


