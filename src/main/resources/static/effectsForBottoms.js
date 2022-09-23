const effectsForBottomList = document.querySelectorAll("input");
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

effectsForBottomList.item(effectsForBottomList.length).style.color = 'blue';
effectsForBottomList.item(effectsForBottomList.length).style.backgroundColor = 'white';
effectsForBottomList.item(effectsForBottomList.length).onmouseleave = () => {
    effectsForBottomList.item(effectsForBottomList.length).style.color = 'blue';
    effectsForBottomList.item(effectsForBottomList.length).style.backgroundColor = 'white'
};
effectsForBottomList.item(effectsForBottomList.length).onmouseover = () => {
    effectsForBottomList.item(effectsForBottomList.length).style.color = 'white';
    effectsForBottomList.item(effectsForBottomList.length).style.backgroundColor = 'blue'
};