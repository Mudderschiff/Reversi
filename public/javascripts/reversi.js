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


let gameJson = {
    size:8,
    player:2,
    0: {0:0,1:0,2:0,3:0,4:0,5:0,6:0,7:0},
    1: {0:0,1:0,2:0,3:0,4:0,5:0,6:0,7:0},
    2: {0:0,1:0,2:0,3:3,4:0,5:0,6:0,7:0},
    3: {0:0,1:0,2:3,3:1,4:2,5:0,6:0,7:0},
    4: {0:0,1:0,2:0,3:2,4:1,5:3,6:0,7:0},
    5: {0:0,1:0,2:0,3:0,4:3,5:0,6:0,7:0},
    6: {0:0,1:0,2:0,3:0,4:0,5:0,6:0,7:0},
    7: {0:0,1:0,2:0,3:0,4:0,5:0,6:0,7:0},
};

function row(scalar) {
    return Math.floor(scalar / size);
}

function col(scalar) {
    return (Math.floor(scalar % size));
}

class Grid {
    constructor(size){
        this.size = size;
        this.cells = [];
    }

    fill(json) {
        for (let scalar=0; scalar <this.size*this.size;scalar++) {
            this.cells[scalar]=(json[row(scalar)][col(scalar)]);
        }
    }
}

let grid = new Grid(gameJson.size);
console.log(col_type(0));
console.log(col_type(2));
console.log(col_type(3));
grid.fill(gameJson)

function fillGrid(grid) {
    for (let scalar=0; scalar <grid.size*grid.size;scalar++) {
        $("#scalar"+scalar).removeClass().addClass(col_type(grid.cells[scalar]))
    }
}

function setCell(scalar, value) {
    console.log("Setting cell " + scalar + " to " + value);
    grid.cells[scalar] = value;
    $("#scalar"+scalar).removeClass().addClass(col_type(2))

}

function registerClickListener() {
    for (let scalar=0; scalar <grid.size*grid.size;scalar++) {
        if (grid.cells[scalar] == 3) {
            $("#scalar"+scalar).click(
                function() {
                    setCell(scalar, grid.cells[scalar])
                }

                );
        }
    }
}

$( document ).ready(function() {
    console.log( "Document is ready, filling grid" );
    fillGrid(grid);
    registerClickListener();

});


