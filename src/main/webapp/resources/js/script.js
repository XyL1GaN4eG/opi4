$(document).ready(function () {
    let radio = parseFloat($("#graphForm\\:inputR").val());
    let pointsByR = {};

    function checkEnter(x, y, r) {
        if (x <= 0 && y >= 0) {
            if (y <= x + (r / 2.0)) {
                return true;
            }
        }

        if (x >= -r && x <= 0) {
            if (y >= -(r / 2.0)) {
                return true;
            }
        }

        if (x >= 0 && y >= 0) {
            if (Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(r, 2)) {
                return true;
            }
        }
        return false;
    }


    let board = JXG.JSXGraph.initBoard("jxgbox", {
        boundingbox: [-6, 6, 6, -6],
        axis: true,
        defaultAxes: {
            x: {
                ticks: {
                    color: 'red',
                    drawZero: true,
                    label: {
                        color: 'red',
                        drawZero: true
                    }
                }
            },
            y: {
                ticks: {
                    color: 'red',
                    label: {
                        color: 'red'
                    }
                }
            }
        }
    });

    let graph1, graph2, graph3, i1, i2, i3;


    function redrawGraphs() {


        if (graph1) board.removeObject(graph1);
        if (i1) board.removeObject(i1);
        if (graph2) board.removeObject(graph2);
        if (i2) board.removeObject(i2);
        if (graph3) board.removeObject(graph3);
        if (i3) board.removeObject(i3);

        graph1 = board.create('functiongraph', [function (x) {
            return x + (radio / 2);
        }, -(radio / 2), 0]);

        i1 = board.create('integral', [[-(radio / 2), 0], graph1], {
            label: {visible: false},
            curveLeft: {
                visible: false
            },
            curveRight: {
                visible: false
            },
            baseRight: {
                visible: false
            },
            baseLeft: {
                visible: false
            },
            fillColor: 'blue',
        });

        graph2 = board.create('functiongraph', [function (x) {
            return -radio / 2
        }, -radio, 0]);
        i2 = board.create('integral', [[-radio, 0], graph2], {
            label: {visible: false},
            curveLeft: {
                visible: false
            },
            curveRight: {
                visible: false
            },
            baseRight: {
                visible: false
            },
            baseLeft: {
                visible: false
            },
            fillColor: 'blue',
        });

        graph3 = board.create('functiongraph', [function (x) {
            return Math.sqrt(radio * radio - x * x);
        }, 0, radio]);
        i3 = board.create('integral', [[0, radio], graph3], {
            label: {visible: false},
            curveLeft: {
                visible: false
            },
            curveRight: {
                visible: false
            },
            baseRight: {
                visible: false
            },
            baseLeft: {
                visible: false
            },
            fillColor: 'blue',
        });
        for (let r in pointsByR) {
            pointsByR[r].forEach(point => point.setAttribute({visible: false}));
        }


        if (pointsByR[radio]) {
            pointsByR[radio].forEach(point => point.setAttribute({visible: true}));
        }
    }


    redrawGraphs();



    response = pointsglobalvalue;
    console.log(response);


    let obj = pointsglobalvalue;

    obj.forEach(data => {
        console.log(data.x, data.y, data.r, data.flag)
        const {x, y, r} = data;
        if (!pointsByR[r]) pointsByR[r] = [];


        if (!pointExists(x, y)) {
            if (data.flag == true) {
                let point = board.create('point', [x, y], {
                    color: 'purple',
                    label: {visible: false}
                });
                pointsByR[r].push(point);
                console.log(`Loaded point at: (${x}, ${y}) for radio: ${r}`);
            } else {
                let point = board.create('point', [x, y], {
                    color: 'red',
                    label: {visible: false}
                });
                pointsByR[r].push(point);
                console.log(`Loaded point at: (${x}, ${y}) for radio: ${r}`);
            }

        }
    });





    redrawGraphs();

    $("#graphForm\\:inputR").on("change", function () {
        radio = parseFloat($(this).val());
        if (isNaN(radio)) {
            console.error("Invalid R value:", $(this).val());
            return;
        }
        redrawGraphs();
    });

    function pointExists(x, y) {
        if (!pointsByR[radio]) return false;

        for (let point of pointsByR[radio]) {
            if (Math.abs(point.X() - x) < 0.0001 && Math.abs(point.Y() - y) < 0.0001) {
                console.log(`Point exists at: (${point.X()}, ${point.Y()})`);
                return true;
            }
        }
        console.log(`No existing point found for: (${x}, ${y})`);
        return false;
    }




    var getMouseCoords = function (e, i) {
        var pos = board.getMousePosition(e, i);
        return new JXG.Coords(JXG.COORDS_BY_SCREEN, pos, board);
    }
    var handleDown = function (e) {
        var canCreate = true,
            i, coords;

        if (e[JXG.touchProperty]) {
            i = 0;
        }
        coords = getMouseCoords(e, i);


        if (pointExists(coords.usrCoords[1], coords.usrCoords[2])) {
            console.log(`Point already exists at: (${coords.usrCoords[1]}, ${coords.usrCoords[2]}), not sending request.`);
            return;
        }

            var xInput = document.getElementById("graphForm:hiddenX");
            var yInput = document.getElementById("graphForm:hiddenY");
            var rInput = document.getElementById("graphForm:hiddenR");


                xInput.value = coords.usrCoords[1];
                yInput.value = coords.usrCoords[2];
                rInput.value = radio;

                document.getElementById('graphForm:submitButton').click();

                let point = board.create('point', [coords.usrCoords[1], coords.usrCoords[2]], {
                    color: 'purple',
                    label: {visible: false}
                })
                if (!pointsByR[radio]) pointsByR[radio] = [];
                pointsByR[radio].push(point);
                console.log(`Point added to pointsByR at (${coords.usrCoords[1]}, ${coords.usrCoords[2]}) for radio: ${radio}`);

                board.removeObject(point);

    };

    board.on('down', handleDown);

    $('#graphForm\\:submitButton').click(function () {
        let x = parseFloat($('#graphForm\\:hiddenX').val());
        let y = parseFloat($('#graphForm\\:hiddenY').val());
        let r = parseFloat($('#graphForm\\:hiddenR').val());

        if (!pointExists(x, y)) {

            if(checkEnter(x, y, r)) {
                var point = board.create('point', [x, y], {
                    color: 'purple',
                    label: {visible: false}
                });
            } else {
                var point = board.create('point', [x, y], {
                    color: 'red',
                    label: {visible: false}
                });
            }
            if (!pointsByR[r]) pointsByR[r] = [];
            pointsByR[r].push(point);
            console.log(`Point created at: (${x}, ${y})`);
        } else {
            console.log(`Point at (${x}, ${y}) already exists, not creating.`);
        }
    });
});