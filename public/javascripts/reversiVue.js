let size2 = 8;
let gridHouses = [cells(0),cells(1),cells(2),cells(3),cells(4),cells(5),cells(6),cells(7)]

function cells(house) {
    let reversiCells = []
    for (let cell = 0; cell < 8; cell++) {
        reversiCells.push({house: house, cell: cell,scalar: "scalar" + toScalar(house,cell)})
    }
    return reversiCells
}
$(document).ready(function () {
    var sudokuGame = new Vue({
        el:'#reversi-game'
    })

})
function toScalar(house,cell) {
    return house*size2 + cell
}


function row(scalar) {
    return Math.floor(scalar / size2);
}

function col(scalar) {
    return Math.floor(scalar % size2);
}

Vue.component('reversi-field', {
    template:`
        <div class="gamecontainer">
            <div class="game">
                <div v-for="house in houses" class="clear">
                    <div v-for="cell in house" v-bind:id="cell.scalar"></div>
                </div>
            </div>
        </div>
    `,
    data: function () {
        return {
            houses: gridHouses
        }
    },

})