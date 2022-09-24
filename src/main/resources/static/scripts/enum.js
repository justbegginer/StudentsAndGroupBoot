const countable1 = document.querySelectorAll("a");
for (let i = 0; i < countable1.length; i++) {
    if (i % 2 === 0){
        countable1.item(i).style.color = 'gold';
        countable1.item(i).onmouseleave = () => {countable1.item(i).style.color = 'gold'};
    }
    else{
        countable1.item(i).style.color = 'yellow';
        countable1.item(i).onmouseleave = () => {countable1.item(i).style.color = 'yellow'};
    }
    countable1.item(i).onmouseover = () => {countable1.item(i).style.color = 'yellowgreen'};
}
