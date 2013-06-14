
// helper functions
var removeByAttr = function (arr, attr, value) {
    var i = arr.length;
    while (i--) {
        if (arr[i] && eval('arr[i].' + attr) && (arguments.length > 2 && eval('arr[i].' + attr) == value )) {
            arr.splice(i, 1);
        }
    }
    return arr;
};

var map = L.map('map').setView([37.8, -96], 4);

var cloudmade = L.tileLayer('http://{s}.tile.cloudmade.com/{key}/{styleId}/256/{z}/{x}/{y}.png', {
    attribution: 'Map data &copy; 2011 OpenStreetMap contributors, Imagery &copy; 2011 CloudMade',
    key: '528344d8866040d9949eebba4c120974',
    styleId: 98735
}).addTo(map);


// control that shows state info on hover
var info = L.control();

info.onAdd = function (map) {
    this._div = L.DomUtil.create('div', 'info');
    this.update();
    return this._div;
};

info.update = function (props) {
    this._div.innerHTML = '<h4>SEIR Distribution</h4>' + (props ?
        '<b>' + props.name + '</b><br />' + props.S + '%S' +
        '<br />' + props.I + '%E' +
        '<br />' + props.E + '%I' +
        '<br />' + props.R + '%R'

        : 'Hover over a region');
};

info.addTo(map);




// controls
var mapSeirControl = L.control({
    position: 'topleft'
});

mapSeirControl.onAdd = function (map) {
    this._div = L.DomUtil.create('div', 'mapSeirControl');
    this._div.innerHTML = '<button id=\"maxextent\" value=\"maxextent\"/>Zoom to US</button><br />';
    return this._div;
};

mapSeirControl.addTo(map);

$("#maxextent").click(function () {
    map.setView([37.8, -96], 4);
})



// get color depending on population density value
function getColor(d) {
    return d > 90 ? '#800026' :
    d > 80 ? '#800026' :
    d > 70 ? '#BD0026' :
    d > 60 ? '#E31A1C' :
    d > 50 ? '#FC4E2A' :
    d > 40 ? '#FD8D3C' :
    d > 30 ? '#FEB24C' :
    d > 20 ? '#FED976' :
    d > 10 ? '#FFEDA0' :
    '#FFFFCC';
}

function style(feature) {
    return {
        weight: 1,
        opacity: 1,
        color: 'white',
        dashArray: '3',
        fillOpacity: 0.5,
        fillColor: getColor(feature.properties.E + feature.properties.I)
    };
}

function highlightFeature(e) {
    var layer = e.target;

    layer.setStyle({
        weight: 1,
        color: '#666',
        dashArray: '',
        fillOpacity: 0.7
    });

    if (!L.Browser.ie && !L.Browser.opera) {
        layer.bringToFront();
    }

    info.update(layer.feature.properties);
}

var geojson;
function resetHighlight(e) {
    geojson.resetStyle(e.target);
    info.update();
}
function zoomToFeature(e) {
    map.fitBounds(e.target.getBounds());
}
function onEachFeature(feature, layer) {
    layer.on({
        mouseover: highlightFeature,
        mouseout: resetHighlight,
        click: zoomToFeature
    });
}


map.attributionControl.addAttribution('Copyright 2013 University of Pittsburgh');


var legend = L.control({
    position: 'bottomright'
});
legend.onAdd = function (map) {

    var div = L.DomUtil.create('div', 'info legend'),
    grades = [0, 10, 20, 30, 40, 50, 60, 70, 80, 90],
    labels = [],
    from, to;


    labels.push('%E+I</h4>');
    for (var i = 0; i < grades.length; i++) {
        from = grades[i];
        to = grades[i + 1];

        labels.push(
            '<i style="background:' + getColor(from + 1) + '"></i> ' +
            from + (to ? '&ndash;' + to : '+'));
    }

    div.innerHTML = labels.join('<br>');
    return div;
};
legend.addTo(map);


var geoJsonData = {};
geoJsonData.type = 'FeatureCollection';
geoJsonData.features = [];
function refreshLayerData() {
    if (geojson != null) {
        map.removeLayer(geojson);
    }
    geojson = L.geoJson(geoJsonData, {
        style: style,
        onEachFeature: onEachFeature
    });
    map.addLayer(geojson);
}

// some fake data
//var storedFractionsForAllRegions = {};
//storedFractionsForAllRegions.usFractions = {};
//storedFractionsForAllRegions.usFractions.susceptible = 50;
//storedFractionsForAllRegions.usFractions.infected = 10;
//storedFractionsForAllRegions.usFractions.infectious = 10;
//storedFractionsForAllRegions.usFractions.immune = 30;
//storedFractionsForAllRegions.stateFractions = [];
//storedFractionsForAllRegions.stateFractions[42] = {};
//storedFractionsForAllRegions.stateFractions[42].susceptible = 60;
//storedFractionsForAllRegions.stateFractions[42].infected = 20;
//storedFractionsForAllRegions.stateFractions[42].infectious = 10;
//storedFractionsForAllRegions.stateFractions[42].immune = 10;
//storedFractionsForAllRegions.stateFractions[36] = {};
//storedFractionsForAllRegions.stateFractions[36].susceptible = 20;
//storedFractionsForAllRegions.stateFractions[36].infected = 60;
//storedFractionsForAllRegions.stateFractions[36].infectious = 10;
//storedFractionsForAllRegions.stateFractions[36].immune = 10;
//storedFractionsForAllRegions.countyFractions = [];
//storedFractionsForAllRegions.countyFractions[42003] = {};
//storedFractionsForAllRegions.countyFractions[42003].susceptible = 70;
//storedFractionsForAllRegions.countyFractions[42003].infected = 20;
//storedFractionsForAllRegions.countyFractions[42003].infectious = 5;
//storedFractionsForAllRegions.countyFractions[42003].immune = 5;
//storedFractionsForAllRegions.countyFractions[42101] = {};
//storedFractionsForAllRegions.countyFractions[42101].susceptible = 10;
//storedFractionsForAllRegions.countyFractions[42101].infected = 80;
//storedFractionsForAllRegions.countyFractions[42101].infectious = 5;
//storedFractionsForAllRegions.countyFractions[42101].immune = 5;

// regionalSEIR struct
//var storedFractionsForAllRegions = {};
//storedFractionsForAllRegions.usFractions = {};
//storedFractionsForAllRegions.usFractions.susceptible = 50;
//storedFractionsForAllRegions.usFractions.infected = 10;
//storedFractionsForAllRegions.usFractions.infectious = 10;
//storedFractionsForAllRegions.usFractions.immune = 30;
//storedFractionsForAllRegions.stateFractions = [];
//storedFractionsForAllRegions.stateFractions[42] = {};
//storedFractionsForAllRegions.stateFractions[42].susceptible = 60;
//storedFractionsForAllRegions.stateFractions[42].infected = 20;
//storedFractionsForAllRegions.stateFractions[42].infectious = 10;
//storedFractionsForAllRegions.stateFractions[42].immune = 10;
//storedFractionsForAllRegions.countyFractions = [];
//storedFractionsForAllRegions.countyFractions[42003] = {};
//storedFractionsForAllRegions.countyFractions[42003].susceptible = 70;
//storedFractionsForAllRegions.countyFractions[42003].infected = 20;
//storedFractionsForAllRegions.countyFractions[42003].infectious = 5;
//storedFractionsForAllRegions.countyFractions[42003].immune = 5;

function setDataFeaturesValues(data, i, susceptible, infected, infectious, immune) {
    // get big decimal versions of the values
    var susceptibleBigD = (new BigDecimal("100.0")).multiply(new BigDecimal(susceptible));
    var infectedBigD = (new BigDecimal("100.0")).multiply(new BigDecimal(infected));
    var infectiousBigD = (new BigDecimal("100.0")).multiply(new BigDecimal(infectious));
    var immuneBigD = (new BigDecimal("100.0")).multiply(new BigDecimal(immune));
    // set the scales of the big decimals
    susceptibleBigD = susceptibleBigD.setScale(5, BigDecimal.ROUND_HALF_EVEN);
    infectedBigD.setScale(5, BigDecimal.ROUND_HALF_EVEN);
    infectedBigD.setScale(5, BigDecimal.ROUND_HALF_EVEN);
    infectiousBigD.setScale(5, BigDecimal.ROUND_HALF_EVEN);
    immuneBigD.setScale(5, BigDecimal.ROUND_HALF_EVEN);
    // set the data features values
    data.features[i].properties.S = susceptibleBigD.floatValue();
    data.features[i].properties.E = infectedBigD.floatValue();
    data.features[i].properties.I = infectiousBigD.floatValue();
    data.features[i].properties.R = immuneBigD.floatValue();
}

function refreshMapData(regionalSEIR) {
    $.getJSON('geojson/world.json',function (data) {
        // format the properties
        var i = data.features.length;
        while (i--) {
            if (data.features[i]) {
                var fips = data.features[i].properties.FIPS;
                var name = data.features[i].properties.NAME;
                delete data.features[i].properties;
                data.features[i].properties = {};
                data.features[i].properties.name = name;
                data.features[i].properties.fips = fips;
                setDataFeaturesValues(data, i, 
                    regionalSEIR.usFractions.susceptible,
                    regionalSEIR.usFractions.infected,
                    regionalSEIR.usFractions.infectious,
                    regionalSEIR.usFractions.immune);
            }
        }

        // remove the US from countries
        data.features = removeByAttr(data.features, 'properties.fips', 'US');

        // add the countries
        geoJsonData.features = geoJsonData.features.concat(data.features);

        refreshLayerData();
    });

    $.getJSON('geojson/US.json',function (data) {

        // format the properties
        var i = data.features.length;
        while (i--) {
            if (data.features[i]) {
                var fips = data.features[i].id;
                var name = data.features[i].properties.name;
                delete data.features[i].properties;
                data.features[i].properties = {};
                data.features[i].properties.name = name;
                data.features[i].properties.fips = fips;

                // set default SEIR for each state
                var defaultSEIR = true;
                for (var j in regionalSEIR.stateFractions) {
                    if (data.features[i].properties.fips == j) {
                        defaultSEIR = false;
                        continue;
                    }
                }
                if (defaultSEIR) {
                    setDataFeaturesValues(data, i, 
                        regionalSEIR.usFractions.susceptible,
                        regionalSEIR.usFractions.infected,
                        regionalSEIR.usFractions.infectious,
                        regionalSEIR.usFractions.immune);
                } else {
                    setDataFeaturesValues(data, i, 
                        regionalSEIR.stateFractions[j].susceptible,
                        regionalSEIR.stateFractions[j].infected,
                        regionalSEIR.stateFractions[j].infectious,
                        regionalSEIR.stateFractions[j].immune);
                }
            }
        }

        // remove states that have counties set
        for (var j in regionalSEIR.countyFractions) {
            var stateFIPS = Math.floor(j / 1000);
            data.features = removeByAttr(data.features, 'properties.fips', stateFIPS + '');
        }

        // add new features
        geoJsonData.features = geoJsonData.features.concat(data.features);

        refreshLayerData();

    });


    // load counties if they are set
    for (var j in regionalSEIR.countyFractions) {
        var stateFIPS = Math.floor(j / 1000);


        $.getJSON('geojson/' + stateFIPS + '.json',function (data) {

            // format the properties
            var i = data.features.length;
            while (i--) {
                if (data.features[i]) {
                    var fips = data.features[i].properties.STATE + data.features[i].properties.COUNTY;
                    var name = data.features[i].properties.NAME;
                    delete data.features[i].properties;
                    data.features[i].properties = {};
                    data.features[i].properties.name = name;
                    data.features[i].properties.fips = fips;

                    // set specific and default SEIR for each county
                    var defaultSEIR = true;
                    for (var j in regionalSEIR.countyFractions) {
                        if (data.features[i].properties.fips == j) {
                            defaultSEIR = false;
                        }
                    }
                    if (defaultSEIR) {
                        if (typeof(regionalSEIR.stateFractions[stateFIPS]) != "undefined") {
                            setDataFeaturesValues(data, i, 
                                regionalSEIR.stateFractions[stateFIPS].susceptible,
                                regionalSEIR.stateFractions[stateFIPS].infected,
                                regionalSEIR.stateFractions[stateFIPS].infectious,
                                regionalSEIR.stateFractions[stateFIPS].immune);
                        } else {
                            setDataFeaturesValues(data, i, 
                                regionalSEIR.usFractions.susceptible,
                                regionalSEIR.usFractions.infected,
                                regionalSEIR.usFractions.infectious,
                                regionalSEIR.usFractions.immune);
                        }
                    } else {
                        setDataFeaturesValues(data, i, 
                            regionalSEIR.countyFractions[j].susceptible,
                            regionalSEIR.countyFractions[j].infected,
                            regionalSEIR.countyFractions[j].infectious,
                            regionalSEIR.countyFractions[j].immune);

                    }
                }
            }


            // add new features to layer
            geoJsonData.features = geoJsonData.features.concat(data.features);

            refreshLayerData();

        });
    }
};

//refreshMapData(storedFractionsForAllRegions);