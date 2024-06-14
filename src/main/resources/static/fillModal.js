async function getUserDataById(userId) {
    const response = await fetch(`/admin/api/users/${userId}`);
    return await response.json();
}

async function getRoles() {
    const response = await fetch(`/admin/api/users/roles`);
    return await response.json();
}


async function fillModal(modal) {

    modal.addEventListener("show.bs.modal", async function (event) {

        const userId = event.relatedTarget.dataset.userId;
        const user = await getUserDataById(userId);
        const rolesConst = await getRoles();

        const modalBody = modal.querySelector(".modal-body");

        const idInput = modalBody.querySelector("input[data-user-id='id']");
        const firstNameInput = modalBody.querySelector("input[data-user-id='firstName']");
        const lastNameInput = modalBody.querySelector("input[data-user-id='lastName']");
        const ageInput = modalBody.querySelector("input[data-user-id='age']");
        const userNameInput = modalBody.querySelector("input[data-user-id='userName']");
        const passwordInput = modalBody.querySelector("input[data-user-id='password']");
        const rolesInput = modalBody.querySelector("input[data-user-id='roles']")

        if (passwordInput !== null) {
            passwordInput.value = user.password;
        }

        idInput.value = user.id;
        firstNameInput.value = user.firstName;
        lastNameInput.value = user.lastName;
        ageInput.value = user.age;
        userNameInput.value = user.userName;
        rolesInput.value = user.roles

        const rolesSelect = document.getElementById("rolesSelect");
        let rolesHtml = "";

        for (let role of rolesConst) {
            rolesHtml +=
                `<option value="${role.id}">${role.name.substring(5).concat(" ").toString().replaceAll(",", "")}
                            </option>`;
        }
        rolesSelect.innerHTML = rolesHtml;


    })
}