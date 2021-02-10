'use strict';

const displayList = document.querySelector("#displayList");
const selectedList = document.querySelector("#selectList");
const createTab = document.querySelector("#create-tab");
const readTab = document.querySelector("#read-tab");
const updateTab = document.querySelector("#update-tab");
const deleteTab = document.querySelector("#delete-tab");

console.log(selectedList);

const printListToScreen = (description, isDone) => {
    let listResult = document.createElement("p");
    let text = document.createTextNode(`${description}`);
    if (isDone == true) {
        listResult.style = "text-decoration: line-through";
    } else { };
    listResult.appendChild(text);
    displayList.appendChild(listResult);
}

const retrieveData = () => {
    const idValue = selectedList.value;
    let data = {
        id: idValue
    }
    console.log(data);

    fetch(`http://localhost:8080/List/read/${data.id}`)
        .then((response) => {
            if (response.status !== 200) {
                console.log(response);
                throw new Error("I don't have a status of 200");
            } else {
                console.log(`response is OK (200)`);
                //json-ify it (which returns a promise)
                displayList.innerHTML = "";
                response.json().then((infoList) => {
                    console.log(infoList);
                    console.log(infoList.taskList); // key - return array
                    for (let task of infoList.taskList) {
                        console.log(task.description);
                        printListToScreen(task.description, task.isDone);
                    }
                })
            }
        }).catch((err) => {
            console.error(err);
        })
}