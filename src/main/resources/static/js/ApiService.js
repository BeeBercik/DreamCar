
class ApiService {
    static async getAllOffers() {
        const response = await fetch("/api/allOffers");
        if(!response.ok) throw new error("Some problems with loading offers.."); 
        
        return await response.json();
    }

    static async getOfferDetails(id) {
        const response = await fetch("/api/offerDetails/" + id);
        if(!response.ok) throw new error("Some problems with offer details..");
        
        return await response.json();;
    }

    static async registerUser(userData) {
        const respone = await fetch("/api/registerUser", {
            method: 'POST',
            headers: {'Content-type': 'application/JSON'},
            body: JSON.stringify(userData)
        });

        const message = await respone.text(); 
        if(respone.ok) navigateTo('login', true, message);
        else UI.showIncorrectRegisterMessage(message);
    }

    static async loginUser(userData) {
        const response = await fetch('/api/loginUser', {
            method: 'POST',
            headers: {'Content-type': 'application/JSON'},
            body: JSON.stringify(userData)
        });

        if(response.ok) navigateTo('index');
        else {
            const message = await response.text();
            UI.showIncorrectLoginMessage(message);
        }
    }

    static async logoutUser() {
        const response = await fetch('/api/logoutUser');
        if(!response.ok) throw new error("Some problems with logging out..");
        navigateTo('index');
    }

    static async checkIfUserLoggedIn() {
        const response = await fetch("/api/isUserLoggedIn");
        if(response.ok) {
            UI.updateUiForUser(); 
            return await response.json();
        } else UI.updateUiForGuest();
    }

    static async getLoggedUser() {
        const response = await fetch("/api/isUserLoggedIn");
        if(response.ok) return await response.json();
        else {
            alert('Session expired. Log in again');
            navigateTo('login');
        }
    }

    static async getUserOffers() {
        const response = await fetch("/api/getUserOffers");
        if(response.ok) {
            const offers = await response.json();
            UI.loadUserOffers(offers);
        } else {
            alert('Session expired. Log in again');
            navigateTo('login');
        };
    }

    static async addNewOffer(offerData) {
        const response = await fetch('/api/addNewOffer', {
            method: 'POST',
            headers: {'Content-type': 'application/JSON'},
            body: JSON.stringify(offerData)
        });

        const message = await response.text();
        if(response.ok)
            navigateTo('user-profile', true, message);
        else  UI.showIncorrectAddingNewOfferMessage(message);
    }
}