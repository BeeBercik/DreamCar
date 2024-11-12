
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
        const form_div_message = document.getElementById('form-message');
        if(!respone) {
            form_div_message.classList.add('incorrect-data');
            form_div_message.innerHTML = message;
        } else {
            form_div_message.innerHTML = message;
        }
    }
}