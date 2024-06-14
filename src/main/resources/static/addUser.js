async function getRoles() {
    const response = await fetch("/admin/api/users/roles");
    return await response.json();
}


async function createNewUser(user) {

    let  responseCreate = await fetch("/admin/api/users",
        {method: 'POST', headers: {'Content-Type': 'application/json'}, body: JSON.stringify(user)})

    while (responseCreate.status < 200 || responseCreate.status > 299){
        alert("Ошибка ввода данных: " + await responseCreate.text())
    }

    await fillTableOfAllUsers();

}

async function addNewUserForm() {

    const newUserForm = document.getElementById("newUser");

    newUserForm.addEventListener('submit', async function (event) {
        event.preventDefault();

        const firstName = newUserForm.querySelector("#firstNameNew").value.trim()
        const lastName = newUserForm.querySelector("#lastNameNew").value.trim();
        const age = newUserForm.querySelector("#ageNew").value.trim();
        const userName = newUserForm.querySelector("#userNameNew").value.trim();
        const password = newUserForm.querySelector("#passwordNew").value.trim();
        const rolesSelected = document.getElementById("roles");


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

        const newUserData = {
            firstName: firstName,
            lastName: lastName,
            age: age,
            userName: userName,
            password: password,
            roles: roles
        };

        await createNewUser(newUserData);
        newUserForm.reset();

        document.querySelector('a#show-users-table').click();
        await fillTableOfAllUsers();
    });
}