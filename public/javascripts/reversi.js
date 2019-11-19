let size = 8;

function col_type(value) {
    switch (value) {
        case 1:
            return "cell-one";
        case 2:
            return "cell-two";
        case 3:
            return "cell-candidate";
        default:
            return "cell";
    }
}

function row(scalar) {
    return Math.floor(scalar / size);
}

function col(scalar) {
    return Math.floor(scalar % size);
}

function toScalar(house,cell) {
    return house*size + cell
}

class Grid {
    constructor(size, player){
        this.size = size;
        this.player = player;
        this.cells = [];
    }

    fill(json) {
        for (let scalar=0; scalar <this.size*this.size;scalar++) {
            this.cells[scalar]=(json[toScalar(row(scalar),col(scalar))].cell);
        }
    }
}

let grid = new Grid(8);

function updateGrid(grid) {
    for (let scalar=0; scalar <grid.size*grid.size;scalar++) {
        $("#scalar"+scalar).removeClass().addClass(col_type(grid.cells[scalar]))
    }
}


function setCell(scalar, value) {
    console.log("Setting cell " + scalar + " to " + value);
    grid.cells[scalar] = value;
    $("#scalar"+scalar).removeClass().addClass(col_type(value))
    setCellOnServer(row(scalar), col(scalar));
    $("#scalar"+scalar).off("click");
    loadJson()
}

function registerClickListener() {
    for (let scalar=0; scalar <grid.size*grid.size;scalar++) {
        if (grid.cells[scalar] == 3) {
            $("#scalar"+scalar).click(
                function() {
                    console.log(grid.player);
                    setCell(scalar, grid.player)
                }

                );
        }
    }
}
function setCellOnServer(row,col) {
    console.log(row,col);
    $.get("/set/"+row+"/"+col, function(data) {
        console.log("Set cell on Server");
    });
}

function loadJson() {
    $.ajax({
        method: "GET",
        url: "/json",
        dataType: "json",

        success: function (result) {
            grid = new Grid(result.grid.size, result.grid.player);
            grid.fill(result.grid.cells);
            updateGrid(grid);
            registerClickListener();
        }
    });
}


$( document ).ready(function() {
    console.log( "Document is ready, filling grid" );
    loadJson();
});


