const countable = document.querySelectorAll("a");
for (let i = 0; i < countable.length; i++) {
    if (i % 2 === 0){
        countable.item(i).style.color = 'gold';
        countable.item(i).onmouseleave = () => {countable.item(i).style.color = 'gold'};
    }
    else{
        countable.item(i).style.color = 'yellow';
        countable.item(i).onmouseleave = () => {countable.item(i).style.color = 'yellow'};
    }
    countable.item(i).onmouseover = () => {countable.item(i).style.color = 'yellowgreen'};
}
