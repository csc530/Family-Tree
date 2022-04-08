/// <reference path="familytree.d.ts" />

var family = new FamilyTree(document.getElementById("tree"), {
    enableSearch: false,
    template: "hugo",
    nodeBinding: {
        field_0: "name",
        img_0: 'img'
    },
    nodes: JSON.parse(Android.getNodes()),
    mouseScrool: FamilyTree.action.none,
    scaleInitial: FamilyTree.match.boundary
});

family.on('click', function (sender, args) {
    Android.showDetails(args.node.id);
    return false;
});
const nodes = JSON.parse(Android.getNodes())