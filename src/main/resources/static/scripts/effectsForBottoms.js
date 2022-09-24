let effectsForBottomList = document.querySelectorAll("input");
console.log(effectsForBottomList.length)
for (let i = 0; i < effectsForBottomList.length; i++) {
    if (i % 2 === 0) {
        effectsForBottomList.item(i).style.color = 'orangered';
        effectsForBottomList.item(i).style.backgroundColor = 'white';
        effectsForBottomList.item(i).onmouseleave = () => {
            effectsForBottomList.item(i).style.color = 'orangered';
            effectsForBottomList.item(i).style.backgroundColor = 'white'
        };
        effectsForBottomList.item(i).onmouseover = () => {
            effectsForBottomList.item(i).style.color = 'white';
            effectsForBottomList.item(i).style.backgroundColor = 'orangered'
        };
    } else {
        effectsForBottomList.item(i).style.color = 'orange';
        effectsForBottomList.item(i).style.backgroundColor = 'white';
        effectsForBottomList.item(i).onmouseleave = () => {
            effectsForBottomList.item(i).style.color = 'orange';
            effectsForBottomList.item(i).style.backgroundColor = 'white'
        };
        effectsForBottomList.item(i).onmouseover = () => {
            effectsForBottomList.item(i).style.color = 'white';
            effectsForBottomList.item(i).style.backgroundColor = 'orange'
        };

    }
}
console.log("hello")
effectsForBottomList.item(effectsForBottomList.length - 1).style.color = 'blue';
effectsForBottomList.item(effectsForBottomList.length - 1).style.backgroundColor = 'white';
effectsForBottomList.item(effectsForBottomList.length - 1).onmouseleave = () => {
    effectsForBottomList.item(effectsForBottomList.length - 1).style.color = 'blue';
    effectsForBottomList.item(effectsForBottomList.length - 1).style.backgroundColor = 'white'
};
effectsForBottomList.item(effectsForBottomList.length - 1).onmouseover = () => {
    effectsForBottomList.item(effectsForBottomList.length - 1).style.color = 'white';
    effectsForBottomList.item(effectsForBottomList.length - 1).style.backgroundColor = 'blue'
};