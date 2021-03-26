
const placeSearch = document.getElementById("place_search");

let map;
let service;

function initMap() {

    const seoul = { lat: 37.552, lng: 126.991 };
    const map = new google.maps.Map(document.getElementById("map"), {
        zoom: 12,
        center: seoul,
    });
    let service = new google.maps.places.PlacesService(map);
    // Configure the click listener.
    map.addListener("click", (mapsMouseEvent) => {
        const clickLatLngJSON = mapsMouseEvent.latLng.toJSON();
        console.log(clickLatLngJSON);

        let clickLatLng = new google.maps.LatLng(clickLatLngJSON.lat, clickLatLngJSON.lng);

        let request = {
            location: clickLatLng,
            radius: '30'
        };

        service.nearbySearch(request, function(results, status) {
            if (status === google.maps.places.PlacesServiceStatus.OK) {
                console.log(results[0].place_id);
                service.getDetails({
                    placeId: results[0].place_id
                }, function(results, status) {
                    if (status === google.maps.places.PlacesServiceStatus.OK) {
                        console.log(results);
                    }
                })
                // for (let i = 0; i < results.length; i++) {
                // 	createMarker(results[i]);
                // }
                // map.setCenter(results[0].geometry.location);
            } else {
                console.log(status);
            }
        });
    });

    document.getElementById("place_search_button")
        .addEventListener('click', () => {
            console.log(placeSearch.value);
            service.findPlaceFromQuery({
                query: placeSearch.value,
                fields: ['name', 'geometry']
            }, function(results, status) {
                if (status === google.maps.places.PlacesServiceStatus.OK) {
                    console.log(results);
                    map.setCenter(results[0].geometry.location);
                }
            });
        });
}

function createMarker(place) {
    if (!place.geometry || !place.geometry.location) return;
    const marker = new google.maps.Marker({
        map,
        position: place.geometry.location,
    });
    google.maps.event.addListener(marker, "click", () => {
        infowindow.setContent(place.name || "");
        infowindow.open(map);
    });
}