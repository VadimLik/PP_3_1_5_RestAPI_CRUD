async function getRoles() {
    const response = await fetch(`/admin/api/users/roles`);
    return await response.json();
}

async function sendDataEditUser(user) {

    let responseEdit = await fetch("/admin/api/users",
        {method: "PUT", headers: {'Content-type': 'application/json;charset=utf-8'}, body: JSON.stringify(user)})

        while (responseEdit.status < 200 || responseEdit.status > 299) {
            alert("Ошибка ввода данных: " + await responseEdit.text());
        }

    await fillTableOfAllUsers();

}

const modalEdit = document.getElementById("editModal");


async function EditModalHandler() {
    await fillModal(modalEdit);
}

modalEdit.addEventListener("submit", async function (event) {
    event.preventDefault();


    const rolesSelected = document.getElementById("rolesEdit");

    let allRole = await getRoles();
    let AllRoles = {};
    for (let role of allRole) {
        AllRoles[role.name] = role.id;
    }
    let roles = [];
    for (let option of rolesSelected.selectedOptions) {
        if (Object.keys(AllRoles).indexOf(option.value) != -1) {
            roles.push({roleId: AllRoles[option.value], name: option.value});
        }
    }

    let user = {
        id: document.getElementById("idEdit").value,
        firstName: document.getElementById("firstNameEdit").value,
        lastName: document.getElementById("lastNameEdit").value,
        age: document.getElementById("ageEdit").value,
        userName: document.getElementById("emailEdit").value,
        password: document.getElementById("passwordEdit").value,
        roles: roles
    }

    await sendDataEditUser(user);

    const modalBootstrap = bootstrap.Modal.getInstance(modalEdit);
    modalBootstrap.hide();
})
