/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//alert("käynnistyykö?");

setInterval(function () {
    var span = document.querySelector("#counter");
    var count = span.textContent * 1 - 1;
    span.textContent = count; 
    
    var inputs = document.querySelectorAll("#remainingTime");
    
    for (var i = 0; i < inputs.length; i++) {
        inputs[i].setAttribute("value", count);
    }
    
//    document.querySelector("#remainingTime").setAttribute("value", count);
    
    if (count <= 0) {
        document.getElementById('next').click();
    }
}, 1000);

function start() {
    var span = document.querySelector("#counter").textContent = 5;
}


