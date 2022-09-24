let deleteBottomList = document.querySelectorAll("input");
deleteBottomList.item(1).style.color = 'red';
deleteBottomList.item(1).style.backgroundColor = 'white';
deleteBottomList.item(1).onmouseleave = () => {
    deleteBottomList.item(1).style.color = 'red';
    deleteBottomList.item(1).style.backgroundColor = 'white'
};
deleteBottomList.item(1).onmouseover = () => {
    deleteBottomList.item(1).style.color = 'white';
    deleteBottomList.item(1).style.backgroundColor = 'red'
};