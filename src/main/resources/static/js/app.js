navigateTo('index');

async function navigateTo(page, flag = false, message = null) {
    const content_div = document.getElementById('content');

    try {
        const response = await fetch(`../${page}.html`);

        if(!response.ok) throw new error("Page not found!");

        const data = await response.text();

        if(page === "index") {
            // spring sam wczyt adomyslnie index
            loadAllOffers();
        } else {
            content_div.innerHTML = data;
        }
        if(page === "register") {
            document.getElementById("register-form").addEventListener('submit', initUserRegister);
        }
        if(page === 'login' && flag) {
            const form_div_message = document.getElementById('form-message');
            form_div_message.classList.add('successfull-register');
            form_div_message.innerHTML = message;
        }
        if(page === "login") {
            document.getElementById('login-form').addEventListener('submit', initUserLogin);
        }
    } catch (error) {
        content_div.innerHTML = `<h2>${error.message}!</h2>`;
    }
}

async function loadAllOffers() {
    try {
        const offers = await ApiService.getAllOffers();
        UI.displayAllOffers(offers);
    } catch(error) {
        document.getElementById("content").innerHTML = error.message;
    }
}

async function showOfferDetails(id) {
    try {
        const offer = await ApiService.getOfferDetails(id);
        UI.displayOfferDetails(offer);
    } catch(error) {
        document.getElementById('content').innerHTML = error.message;
    }
}

async function initUserRegister(event) {
    event.preventDefault();
    try {
        const login = document.getElementById('login').value;
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;
        const rep_password = document.getElementById('rep_password').value
        const phone = document.getElementById('phone').value;

        const userData = {
            login: login,
            email: email,
            password: password,
            rep_password: rep_password,
            phone: phone
        };
        await ApiService.registerUser(userData); 
    } catch(error) {
        document.getElementById('content').innerHTML = error.message;
    }
}

async function initUserLogin(event) {
    event.preventDefault();
    try {
        const login = document.getElementById('login').value;
        const password = document.getElementById('password').value;
        
        const userData = {
            login: login,
            password: password
        }
        await ApiService.loginUser(userData);
    } catch (error) {
        document.getElementById('content').innerHTML = error.message;
    }
}