const countable = document.querySelectorAll("input");
for (let i = 0; i < countable.length - 1; i++) {
    let color;
    if (i % 5 === 0) {
        color = 'orangered';
    } else if (i % 5 === 1) {
        color = 'orange';
    } else if (i % 5 === 2) {
        color = 'green';
    }
    else if (i % 5 === 3){
        color = 'crimson';
    }
    else {
        color = 'purple';
    }
    countable.item(i).style.color = color;
    countable.item(i).style.backgroundColor = 'white';
    countable.item(i).onmouseleave = () => {
        countable.item(i).style.color = color;
        countable.item(i).style.backgroundColor = 'white'
    };
    countable.item(i).onmouseover = () => {
        countable.item(i).style.color = 'white';
        countable.item(i).style.backgroundColor = color;
    };
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