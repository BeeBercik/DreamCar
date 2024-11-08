
class ApiService {
    static async getAllOffers() {
        const response = await fetch("/allOffers");
        if(!response) throw new error("Some problems with loading offers..");
        const offers = await response.json();
        
        return offers;
    }

    static async getOfferDetails(id) {
        const response = await fetch("/offerDetails/" + id);
        if(!response) throw new error("Some problems with offer details..");
        const offer = await response.json();
        
        return offer;
    }
}