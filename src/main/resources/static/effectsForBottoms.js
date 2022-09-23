const countable = document.querySelectorAll("input");
for (let i = 0; i < countable.length - 1; i++) {
    if (i % 2 === 0) {
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
    } else {
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