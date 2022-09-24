let updateBottomList = document.querySelectorAll("input");
for (let i = 0; i < updateBottomList.length; i++) {
    updateBottomList.item(i).style.color = 'orange';
    updateBottomList.item(i).style.backgroundColor = 'white';
    updateBottomList.item(i).onmouseleave = () => {
        updateBottomList.item(i).style.color = 'orange';
        updateBottomList.item(i).style.backgroundColor = 'white'
    };
    updateBottomList.item(i).onmouseover = () => {
        updateBottomList.item(i).style.color = 'white';
        updateBottomList.item(i).style.backgroundColor = 'orange'
    };
}