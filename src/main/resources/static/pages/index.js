'use strict';

const displayList = document.querySelector("#displayList");

const selectList = document.querySelector("#selectList");
const selectListAddTask = document.querySelector("#selectListAddTask");
const selectListDelete = document.querySelector("#selectListDelete");


const alertMsgCreate = document.querySelector("#onCreation");
const alertMsgAdd = document.querySelector("#onAddition");
const alertMsgRead = document.querySelector("#invalidSelection");
const alertMsgDelete = document.querySelector("#onDeletion");


const newListName = document.querySelector("#newListName");
const newTaskDesc = document.querySelector("#newTaskDesc");


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
            alertMsgCreate.setAttribute("class", "alert alert-success");
            alertMsgCreate.innerHTML = "Your to-do list has been created!";
            setTimeout(() => {
                alertMsgCreate.removeAttribute("class");
                alertMsgCreate.innerHTML = "";
            }, 2000);
        })
        .catch(err => console.error(`Stopppppp! ${err}`));
}

const addTaskToList = () => {

    const listIdValue = selectListAddTask.value;
    const taskDescValue = newTaskDesc.value;

    let data = {
        description: `${taskDescValue}`,
        isDone: false,
        myList: {
            id: listIdValue
        }
    }

    fetch("http://localhost:8080/Task/create", {
        method: "POST",
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(response => {
            console.log(`Task ID:${data.id} created`);
            taskDescValue.value = "";
            alertMsgAdd.setAttribute("class", "alert alert-success");
            alertMsgAdd.innerHTML = "Your task has been added!";
            setTimeout(() => {
                alertMsgAdd.removeAttribute("class");
                alertMsgAdd.innerHTML = "";
            }, 2000);
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
            alertMsgRead.setAttribute("class", "alert alert-danger");
            alertMsgRead.innerHTML = "Please select a list!";
            setTimeout(() => {
                alertMsgRead.removeAttribute("class");
                alertMsgRead.innerHTML = "";
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
    let listOption2 = listOption.cloneNode(true);
    selectListAddTask.appendChild(listOption);
    selectList.appendChild(listOption1);
    selectListDelete.appendChild(listOption2);
}

const deleteList = () => {
    const idValue = selectListDelete.value;
    let data = {
        id: idValue
    }

    fetch(`http://localhost:8080/List/delete/${data.id}`, {
        method: "DELETE",
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(response => {
            console.log(`List ID:${data.id} deleted`);
            inputGarageId.value = "";
            alertMsgRead.setAttribute("class", "alert alert-info");
            alertMsgRead.innerHTML = "Your to-do list has been deleted!";
            setTimeout(() => {
                alertMsgRead.removeAttribute("class");
                alertMsgRead.innerHTML = "";
            }, 2000);
        })
        .catch(err => console.error(`Stopppppp! ${err}`));
}


getAllList();