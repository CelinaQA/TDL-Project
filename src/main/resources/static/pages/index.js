'use strict';

const displayList = document.querySelector("#displayList");
const selectList = document.querySelector("#selectList");
const selectListAddTask = document.querySelector("#selectListAddTask");
const alertMsg = document.querySelector("#onsuccess");
const newListName = document.querySelector("#newListName");

const createTab = document.querySelector("#create-tab");
const readTab = document.querySelector("#read-tab");
const updateTab = document.querySelector("#update-tab");
const deleteTab = document.querySelector("#delete-tab");

console.log(selectList);

const createList = () => {

    const nameValue = newListName.value;

    let data = {
        name: nameValue
    }

    fetch("http://localhost:8080/List/create", {
        method: "POST",
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(response => {
            console.log(`${data.name} created`);
            inputName.value = "";
        })
        .catch(err => console.error(`Stopppppp! ${err}`));
}

const printListToScreen = (num, description, isDone) => {
    let listResult = document.createElement("p");
    let text = document.createTextNode(`${num}. ${description}`);
    if (isDone == true) {
        listResult.style = "text-decoration: line-through";
    } else { };
    listResult.appendChild(text);
    displayList.appendChild(listResult);
}

const clearDisplayList = () => {
    displayList.innerHTML = "";
}

const readList = () => {
    const idValue = selectList.value;
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
                        let num = infoList.taskList.indexOf(task) + 1;
                        printListToScreen(num, task.description, task.isDone);
                    }
                })
            }
        }).catch((err) => {
            console.error(err);
            clearDisplayList();
            alertMsg.setAttribute("class", "alert alert-danger");
            alertMsg.innerHTML = "Please select a list!";
            setTimeout(() => {
                alertMsg.removeAttribute("class");
                alertMsg.innerHTML = "";
            }, 2000);
        })
}

const getAllList = () => {
    fetch(`http://localhost:8080/List/readAll`)
        .then((response) => {
            if (response.status !== 200) {
                console.log(response);
                throw new Error("I don't have a status of 200");
            } else {
                console.log(`response is OK (200)`);
                //json-ify it (which returns a promise)
                response.json().then((infoList) => {
                    console.log(infoList); // key - return array
                    // selectListAddTask.innerHTML = "";
                    // selectList.innerHTML = "";
                    for (let list of infoList) {
                        console.log(list.name);
                        let listName = list.name;
                        let listId = list.id;
                        updateExistingLists(listName, listId);
                    }
                })
            }
        }).catch((err) => {
            console.error(err);
        })
}


const updateExistingLists = (listName, listId) => {
    let text = document.createTextNode(listName);
    let listOption = document.createElement("option")
    listOption.value = `${listId}`;
    listOption.appendChild(text);
    let listOption1 = listOption.cloneNode(true);
    selectListAddTask.appendChild(listOption);
    selectList.appendChild(listOption1);
    console.log(listOption)
    console.log("--------------")
    console.log(listOption1)
}

getAllList();