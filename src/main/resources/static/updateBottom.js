let updateBottomList = document.querySelectorAll("input");
updateBottomList.item(updateBottomList.length - 1).style.color = 'orange';
updateBottomList.item(updateBottomList.length - 1).style.backgroundColor = 'white';
updateBottomList.item(updateBottomList.length - 1).onmouseleave = () => {
    updateBottomList.item(updateBottomList.length - 1).style.color = 'orange';
    updateBottomList.item(updateBottomList.length - 1).style.backgroundColor = 'white'
};
updateBottomList.item(updateBottomList.length - 1).onmouseover = () => {
    updateBottomList.item(updateBottomList.length - 1).style.color = 'white';
    updateBottomList.item(updateBottomList.length - 1).style.backgroundColor = 'orange'
};