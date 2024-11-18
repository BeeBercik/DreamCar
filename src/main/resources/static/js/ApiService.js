
class ApiService {
    static async getAllOffers() {
        const response = await fetch("/api/allOffers");
        if(!response) throw new error("Some problems with loading offers..");
        const offers = await response.json();
        
        return offers;
    }

    static async getOfferDetails(id) {
        const response = await fetch("/api/offerDetails/" + id);
        if(!response) throw new error("Some problems with offer details..");
        const offer = await response.json();
        
        return offer;
    }

    static async registerUser(userData) {
        const respone = await fetch("/api/registerUser", {
            method: 'POST',
            headers: {'Content-type': 'application/JSON'},
            body: JSON.stringify(userData)
        });

        const message = await respone.text(); 
        if(!respone.ok) {
            const form_div_message = document.getElementById('form-message');
            form_div_message.classList.add('incorrect-data');
            form_div_message.innerHTML = message;
        } else {
            navigateTo('login', true, message);
        }
    }

    static async loginUser(userData) {
        const response = await fetch('/api/loginUser', {
            method: 'POST',
            headers: {'Content-type': 'application/JSON'},
            body: JSON.stringify(userData)
        });

        if(response.ok) {
            navigateTo('index');
            console.log('zalogowano')
        } else {
            const message = await response.text();
            const form_div_message = document.getElementById('form-message');
            form_div_message.classList.add('incorrect-data');
            form_div_message.innerHTML = message;
        }
    }

    static async checkIfUserLoggedIn() {
        const response = await fetch("/api/isUserLoggedIn");
        if(response.ok) UI.updateUiForUser(); 
        else UI.updateUiForGuest();
    }

    static async getLoggedUser() {
        const response = await fetch("/api/isUserLoggedIn");
        if(response.ok) {
            const user = await response.json();
            return user;
        } else return null;
    }

    static async logoutUser() {
        const response = await fetch('/api/logoutUser');
        if(!response) throw new error("Some problems with logging out..");
        if(response.ok) navigateTo('index');
    }
}