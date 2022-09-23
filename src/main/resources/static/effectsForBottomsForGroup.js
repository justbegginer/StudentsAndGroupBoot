const countable = document.querySelectorAll("input");
console.log(countable.length)
for (let i = 0; i < countable.length - 1; i++) {
    if (i % 4 === 0) {
        countable.item(i).style.color = 'orangered';
        countable.item(i).style.backgroundColor = 'white';
        countable.item(i).onmouseleave = () => {
            countable.item(i).style.color = 'orangered';
            countable.item(i).style.backgroundColor = 'white'
        };
        countable.item(i).onmouseover = () => {
            countable.item(i).style.color = 'white';
            countable.item(i).style.backgroundColor = 'orangered'
        };
    } else if (i % 4 === 1) {
        countable.item(i).style.color = 'orange';
        countable.item(i).style.backgroundColor = 'white';
        countable.item(i).onmouseleave = () => {
            countable.item(i).style.color = 'orange';
            countable.item(i).style.backgroundColor = 'white'
        };
        countable.item(i).onmouseover = () => {
            countable.item(i).style.color = 'white';
            countable.item(i).style.backgroundColor = 'orange'
        };
    } else if (i % 4 === 2) {
        countable.item(i).style.color = 'green';
        countable.item(i).style.backgroundColor = 'white';
        countable.item(i).onmouseleave = () => {
            countable.item(i).style.color = 'green';
            countable.item(i).style.backgroundColor = 'white'
        };
        countable.item(i).onmouseover = () => {
            countable.item(i).style.color = 'white';
            countable.item(i).style.backgroundColor = 'green'
        };
    } else {
        countable.item(i).style.color = 'purple';
        countable.item(i).style.backgroundColor = 'white';
        countable.item(i).onmouseleave = () => {
            countable.item(i).style.color = 'purple';
            countable.item(i).style.backgroundColor = 'white'
        };
        countable.item(i).onmouseover = () => {
            countable.item(i).style.color = 'white';
            countable.item(i).style.backgroundColor = 'purple'
        };

    }
}

countable.item(countable.length - 1).style.color = 'blue';
countable.item(countable.length - 1).style.backgroundColor = 'white';
countable.item(countable.length - 1).onmouseleave = () => {
    countable.item(countable.length - 1).style.color = 'blue';
    countable.item(countable.length - 1).style.backgroundColor = 'white'
};
countable.item(countable.length - 1).onmouseover = () => {
    countable.item(countable.length - 1).style.color = 'white';
    countable.item(countable.length - 1).style.backgroundColor = 'blue'
};