let deleteUpdateBottoms = document.querySelectorAll("input");
deleteUpdateBottoms.item(0).style.color = 'red';
deleteUpdateBottoms.item(0).style.backgroundColor = 'white';
deleteUpdateBottoms.item(0).onmouseleave = () => {
    deleteUpdateBottoms.item(0).style.color = 'red';
    deleteUpdateBottoms.item(0).style.backgroundColor = 'white'
};
deleteUpdateBottoms.item(0).onmouseover = () => {
    deleteUpdateBottoms.item(0).style.color = 'white';
    deleteUpdateBottoms.item(0).style.backgroundColor = 'red'
};
deleteUpdateBottoms.item(1).style.color = 'orange';
deleteUpdateBottoms.item(1).style.backgroundColor = 'white';
deleteUpdateBottoms.item(1).onmouseleave = () => {
    deleteUpdateBottoms.item(1).style.color = 'orange';
    deleteUpdateBottoms.item(1).style.backgroundColor = 'white'
};
deleteUpdateBottoms.item(1).onmouseover = () => {
    deleteUpdateBottoms.item(1).style.color = 'white';
    deleteUpdateBottoms.item(1).style.backgroundColor = 'orange'
};