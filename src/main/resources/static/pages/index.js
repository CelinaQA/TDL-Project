'use strict';

const displayList = document.querySelector("#displayList");
const displayUpdateList = document.querySelector("#displayUpdateList");

const selectList = document.querySelector("#selectList");
const selectListAddTask = document.querySelector("#selectListAddTask");
const selectListDelete = document.querySelector("#selectListDelete");
const selectListUpdate = document.querySelector("#selectListUpdate");


const alertMsgCreate = document.querySelector("#onCreation");
const alertMsgAdd = document.querySelector("#onAddition");
const alertMsgRead = document.querySelector("#invalidSelection");
const alertMsgUpdateSelect = document.querySelector("#invalidUpdate");
const alertMsgUpdate = document.querySelector("#onUpdate");
const alertMsgDelete = document.querySelector("#onDeletion");

const newListName = document.querySelector("#newListName");
const newTaskDesc = document.querySelector("#newTaskDesc");

const createTab = document.querySelector("#create-tab");
const readTab = document.querySelector("#read-tab");
const updateTab = document.querySelector("#update-tab");
const deleteTab = document.querySelector("#delete-tab");

console.log(selectList);

//=======================================================================
// CREATE
//=======================================================================

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
            alertMsgAdd.innerHTML = "Your task has been created!";
            setTimeout(() => {
                alertMsgAdd.removeAttribute("class");
                alertMsgAdd.innerHTML = "";
            }, 2000);
        })
        .catch(err => console.error(`Stopppppp! ${err}`));

}

//=======================================================================
// READ
//=======================================================================

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
                clearDisplayList();
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

//=======================================================================
// DROPDOWN LISTS UPDATE METHOD
//=======================================================================

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
    let listOption3 = listOption.cloneNode(true);
    selectListAddTask.appendChild(listOption);
    selectList.appendChild(listOption1);
    selectListDelete.appendChild(listOption2);
    selectListUpdate.appendChild(listOption3);
}

//=======================================================================
// UPDATE
//=======================================================================
const clearUpdateDisplayList = () => {
    displayUpdateList.innerHTML = "";
}

const printUpdateTasks = (num, description, isDone, taskId) => {

    let divRow = document.createElement("div");
    divRow.setAttribute("class", "row justify-content-md-center")
    divRow.setAttribute("style", "margin-bottom: 7px;")

    let divForm = document.createElement("div");
    divForm.setAttribute("class", "form-check form-check-inline")

    let listResult = document.createElement("input");
    listResult.setAttribute("class", "form-control");
    listResult.setAttribute("id", `taskDesc-${taskId}`);
    listResult.setAttribute("type", "text");
    listResult.setAttribute("value", `${description}`);
    listResult.setAttribute("style", `width: 400px;`);

    let text = document.createTextNode(`${num}. ${description}`);


    let taskCheck = document.createElement("input");
    taskCheck.setAttribute("class", "form-check-input");
    taskCheck.setAttribute("id", `taskCheck-${taskId}`);
    taskCheck.setAttribute("type", "checkbox");

    if (isDone == true) {

        taskCheck.checked = true;
    } else { };

    listResult.appendChild(text);
    displayUpdateList.appendChild(divRow);
    divRow.appendChild(divForm);
    divForm.appendChild(taskCheck);
    divForm.appendChild(listResult);
}

const readUpdateList = () => {
    const idValue = selectListUpdate.value;
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
                clearUpdateDisplayList();
                response.json().then((infoList) => {
                    console.log(infoList);
                    console.log(infoList.taskList); // key - return array
                    for (let task of infoList.taskList) {
                        console.log(task.description);
                        let num = infoList.taskList.indexOf(task) + 1;
                        printUpdateTasks(num, task.description, task.isDone, task.id);
                    }
                })
            }
        }).catch((err) => {
            console.error(err);
            clearDisplayList();
            alertMsgUpdateSelect.setAttribute("class", "alert alert-danger");
            alertMsgUpdateSelect.innerHTML = "Please select a list!";
            setTimeout(() => {
                alertMsgUpdateSelect.removeAttribute("class");
                alertMsgUpdateSelect.innerHTML = "";
            }, 2000);
        })
}

const updateListTasks1 = () => {

    const idValue = selectListUpdate.value;
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
                response.json().then((infoList) => {
                    console.log(infoList);
                    console.log(infoList.taskList); // key - return array
                    for (let task of infoList.taskList) {
                        console.log(task.description);
                        updateListTasks2(task.id);
                    }
                })
            }
        }).catch((err) => {
            console.error(err);
            clearDisplayList();
            alertMsgUpdateSelect.setAttribute("class", "alert alert-danger");
            alertMsgUpdateSelect.innerHTML = "Please select a list!";
            setTimeout(() => {
                alertMsgUpdateSelect.removeAttribute("class");
                alertMsgUpdateSelect.innerHTML = "";
            }, 2000);
        })
}

const updateListTasks2 = (taskId) => {
    const taskCheck = document.getElementById(`taskCheck-${taskId}`);
    const taskDesc = document.getElementById(`taskDesc-${taskId}`);
    const listId = selectListUpdate.value;

    let data = {
        description: `${taskDesc.value}`,
        isDone: taskCheck.checked,
        myList: {
            id: listId
        }
    }

    console.log(data);

    fetch(`http://localhost:8080/Task/update/${taskId}`, {
        method: "PUT",
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(response => {
            console.log(`Task ID:${taskId} updated`);
            clearUpdateDisplayList();
            alertMsgUpdate.setAttribute("class", "alert alert-success");
            alertMsgUpdate.innerHTML = "Your list has been updated!";
            setTimeout(() => {
                alertMsgUpdate.removeAttribute("class");
                alertMsgUpdate.innerHTML = "";
            }, 2000);
        })
        .catch((err) => {
            console.error(err);
            clearUpdateDisplayList();
            alertMsgUpdate.setAttribute("class", "alert alert-danger");
            alertMsgUpdate.innerHTML = "Update failed!";
            setTimeout(() => {
                alertMsgUpdate.removeAttribute("class");
                alertMsgUpdate.innerHTML = "";
            }, 2000);
        })

}

//=======================================================================
// DELETE
//=======================================================================

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


//=======================================================================
// UPDATE DROPDOWN LISTS
//=======================================================================

getAllList();