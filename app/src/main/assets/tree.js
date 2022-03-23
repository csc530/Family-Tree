/// <reference path="familytree.d.ts" />
var family = new FamilyTree(document.getElementById("tree"), {
    template: "hugo",
    scaleInitial: FamilyTree.match.boundary,
    nodeBinding: {
        field_0: "name",
    },
    nodes: JSON.parse(Android.getNodes()),
});
family.on('click', function (sender, args) {
    Android.showDetails(args.node.id);
    return false;
});
const nodes = JSON.parse(Android.getNodes())
// Android.showToast(nodes.length);
