---
title: "Benchmark"
draft: true
---

<p>
    <div id="configuration"></div>
</p>
<br/>
<p>
    <div style="display: table; width: 100%;">
        <div style="display: table-row;">
            <div style="display: table-cell; width: 50%;"><canvas id="executiontime" /></div>
            <div style="display: table-cell; width: 50%;"><canvas id="agentinitializetime" /></div>
        </div>
        <div style="display: table-row;">
            <div style="display: table-cell; width: 50%"><canvas id="cycletimedistribution" /></div>    
            <div style="display: table-cell; width: 50%"><canvas id="memoryconsumption" /></div>
        </div>
    </div>
</p>    

<script>
// http://bootstrap-table.wenzhixin.net.cn/getting-started/
// https://github.com/datavisyn/chartjs-chart-boxplot

const colormapid = "rainbow-soft";
const timescaling = function(t) { return t / 1000000 };
const memoryscaling = function(m) { return m / Math.pow(1024, 2); };
const timebyloggingrate = function(t,r) { return t * r / 1000; };
const statisticobject = function(o) {
    return {
        min: timescaling( o["min"] ),
        max: timescaling( o["max"] ),
        median: timescaling( o["50-percentile"] || (0.5*(o["max"] - o["min"])) ),
        q1: timescaling( o["25-percentile"] || (0.25*(o["max"] - o["min"])) ),
        q3: timescaling( o["75-percentile"] || (0.75*(o["max"] - o["min"])) )
    }
};

const timeplot = function( dom, frame, title, inputdata, yticklabel ) {

    new Chart(jQuery( dom ), {
        type: "boxplot",
        data: {
            labels: inputdata.scenariosize.map( n => Object.values(n).reduce((x, y) => x + y, 0) ),
            datasets: [{
                borderColor: Array.apply(null, Array(inputdata.time[frame].length)).map(function() { return "rgba(125,125,255,1)" }),
                backgroundColor: Array.apply(null, Array(inputdata.time[frame].length)).map(function() { return "rgba(125,125,255,0.35)" }),
                data: inputdata.time[frame].map( n => statisticobject(n) )
            }]
        },
        options: {
            responsive: true,
            title: {
                display: true,
                text: title
            },
            legend: {
                position: "bottom"
            },
            scales: {
                xAxes: [{
                    scaleLabel: {
                        display: true,
                        labelString: "number of agents"
                    },
                }],
                yAxes: [{
                    type: "arrayLogarithmic",
                    scaleLabel: {
                        display: true,
                        labelString: "time in miliseconds"
                    },
                    ticks: {
                        beginAtZero: true,
                        callback: yticklabel || function(v, i) { return i % 3 ? "" : v.toFixed(2); }
                    }
                }]
            }
        }
    });
};

const memoryplot = function( dom, title, inputdata, yticklabel ) {
    new Chart(jQuery( dom ), {
        type: "line",
        data: {
            labels: Array.from(Array(inputdata.memory.totalmemory.length).keys()).map( n => timebyloggingrate(n, inputdata.configuration.memoryloggingrate).toFixed(0) ),
            datasets: [{
                label: "free memory",
                steppedLine: true,
                radius: 0,
                data: inputdata.memory.freememory.map( n => memoryscaling(n) ),
                fill: false,
                borderColor: [
                    "rgba(50,200,75,1)",
                ],
                borderWidth: 2
            },{
                label: "used memory",
                steppedLine: true,
                radius: 0,
                data: inputdata.memory.usedmemory.map( n => memoryscaling(n) ),
                fill: "-1",
                borderColor: [
                    "rgba(125,125,255,1)",
                ],
                borderWidth: 2
            },{
                label: "total memory",
                steppedLine: true,
                radius: 0,
                data: inputdata.memory.totalmemory.map( n => memoryscaling(n) ),
                fill: "-1",
                borderColor: [
                    "rgba(255,100,135,1)",
                ],
                borderWidth: 2
            }]
        },
        options: {
            responsive: true,
            title: {
                display: true,
                text: title
            },
            legend: {
                position: "bottom"
            },
            scales: {
                xAxes: [{
                    scaleLabel: {
                        display: true,
                        labelString: "benchmarking time in seconds"
                    },
                }],
                yAxes: [{
                    scaleLabel: {
                        display: true,
                        labelString: "memory in megabytes"
                    },
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });
};

const cycleplot = function( dom, title, inputdata, yticklabel ) {

    const bordercolormap = colormap({ colormap: colormapid, format: "rgbaString", nshades: Object.keys(inputdata.time.cycle).length });
    const backgroundcolormap = colormap({ colormap: colormapid, format: "rgbaString", alpha: 0.35, nshades: Object.keys(inputdata.time.cycle).length }); 

    new Chart(jQuery( dom ), {
        type: "boxplot",
        data: {
            labels: inputdata.scenariosize.map( n => Object.values(n).reduce((x, y) => x + y, 0) ),
            datasets: Object.keys(inputdata.time.cycle[0])
                .map(function(i, idx) { 
                    return { 
                        label: i,
                        borderColor: Array.apply(null, Array(bordercolormap.length)).map(function() { return bordercolormap[idx] }),
                        backgroundColor: Array.apply(null, Array(backgroundcolormap.length)).map(function() { return backgroundcolormap[idx] }),
                        data: inputdata.time.cycle.map(n => n[i] ).map( n => statisticobject(n) )
                    } 
                } )
        },
        options: {
            responsive: true,
            title: {
                display: true,
                text: title
            },
            legend: {
                position: "bottom"
            },
            scales: {
                xAxes: [{
                    scaleLabel: {
                        display: true,
                        labelString: "number of agents"
                    },
                }],
                yAxes: [{
                    type: "arrayLogarithmic",
                    scaleLabel: {
                        display: true,
                        labelString: "time in miliseconds"
                    },
                    ticks: {
                        beginAtZero: true,
                        callback: yticklabel || function(v, i) { return i % 3 ? "" : v.toFixed(2); }
                    }
                }]
            }
        }
    });
};

const configurationtable = function( dom, inputdata ) {
    const l_runtimdata = inputdata.configuration.runtime.split( " " );
    jQuery( dom ).append(
        jQuery( "<table>" )
            .append( 
                jQuery( "<tr>" ).append( jQuery( "<th colspan=\"4\">" ).text( "Benchmark Configuration" ) )
             )
            .append(  
                jQuery( "<tr>" )
                    .append( jQuery( "<th>" ).text( "Machine Processors" ) )
                    .append( jQuery( "<td colspan=\"3\">" ).text( inputdata.configuration.processors ) ) 
            )
            .append(
                l_runtimdata[0].includes("synchronized")
                    ? jQuery( "<tr>" )
                        .append( jQuery( "<th>" ).text( "Runtime" ) )
                        .append( jQuery( "<td colspan=\"3\">" ).text( l_runtimdata[0] ) )
                    : jQuery( "<tr>" )
                        .append( jQuery( "<th>" ).text( "Runtime / Threadnumber" ) )
                        .append( jQuery( "<td colspan=\"1\">" ).text( l_runtimdata[0] ) )
                        .append( jQuery( "<td colspan=\"2\">" ).text( l_runtimdata[1] ) )       
            )             
            .append(                
                jQuery("<tr>")
                    .append( jQuery( "<th>" ).text( "Operating System" ) )
                    .append( jQuery( "<td>" ).text( inputdata.configuration.osname ) )
                    .append( jQuery( "<td>" ).text( inputdata.configuration.osversion ) )
                    .append( jQuery( "<td>" ).text( inputdata.configuration.osarchitecture ) )
            )
            .append(
                jQuery( "<tr>" )
                    .append( jQuery( "<th>" ).text( "Java System" ) )
                    .append( jQuery( "<td>" ).text( inputdata.configuration.vmvendor ) )
                    .append( jQuery( "<td>" ).text( inputdata.configuration.vmname ) )
                    .append( jQuery( "<td>" ).text( inputdata.configuration.javaversion + " (" + inputdata.configuration.vmversion + ")" ) )
            )
            .append(  
                jQuery( "<tr>" )
                    .append( jQuery( "<th>" ).text( "Iteration / Warm-Up / Execution" ) )
                    .append( jQuery( "<td>" ).text( inputdata.configuration.iteration ) )
                    .append( jQuery( "<td>" ).text( inputdata.configuration.warmup ) )
                    .append( jQuery( "<td>" ).text( inputdata.configuration.runs ) )
            )
            .append(
                jQuery( "<tr>" )
                    .append( jQuery( "<th>" ).text( "Runtime Arguments" ) )
                    .append( 
                        jQuery( "<td colspan=\"3\">" )
                            .append(
                                jQuery( "<ul>" ).html( inputdata.configuration.runtimearguments.map( n => "<li>" + n + "</li>" ) )
                            )
                    )
            )      
    );
};




jQuery.ajax({
        url: "/synchronizedcount5.json",
        crossDomain: true
    })
    .done(function(data) {
        console.log(data);

        configurationtable( "#configuration", data );
        timeplot( "#executiontime", "execution", "agent execution time", data );
        timeplot( "#agentinitializetime", "agentinitialize", "agent initializing time", data );
        cycleplot( "#cycletimedistribution", "agent cycle time distribution", data );
        memoryplot( "#memoryconsumption", "memory consumption", data );
    });    
</script>
