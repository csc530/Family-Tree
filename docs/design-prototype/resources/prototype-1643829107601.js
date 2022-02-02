(function(window, undefined) {
  var dictionary = {
    "f6b09bb3-0425-43d0-84b2-39916b7a3e1a": "Create New Tree",
    "979142a8-9892-4007-b3c0-314bac6bf5d3": "example",
    "2188005a-75bd-42a9-aa0c-4ce060ca089b": "Add link",
    "3fa7b20f-bfe1-46b3-9935-8b0b7726d103": "Load",
    "d12245cc-1680-458d-89dd-4f0d7fb22724": "Home",
    "f39803f7-df02-4169-93eb-7547fb8c961a": "Template 1",
    "b1525292-6cf3-46ec-b895-3daf05988928": "help",
    "bb8abf58-f55e-472d-af05-a7d1bb0cc014": "default"
  };

  var uriRE = /^(\/#)?(screens|templates|masters|scenarios)\/(.*)(\.html)?/;
  window.lookUpURL = function(fragment) {
    var matches = uriRE.exec(fragment || "") || [],
        folder = matches[2] || "",
        canvas = matches[3] || "",
        name, url;
    if(dictionary.hasOwnProperty(canvas)) { /* search by name */
      url = folder + "/" + canvas;
    }
    return url;
  };

  window.lookUpName = function(fragment) {
    var matches = uriRE.exec(fragment || "") || [],
        folder = matches[2] || "",
        canvas = matches[3] || "",
        name, canvasName;
    if(dictionary.hasOwnProperty(canvas)) { /* search by name */
      canvasName = dictionary[canvas];
    }
    return canvasName;
  };
})(window);jQuery("#simulation")
  .on("click", ".s-f6b09bb3-0425-43d0-84b2-39916b7a3e1a .click", function(event, data) {
    var jEvent, jFirer, cases;
    if(jimUtil.isAlternateModeActive()) return;
    if(data === undefined) { data = event; }
    jEvent = jimEvent(event);
    jFirer = jEvent.getEventFirer();
    if(jFirer.is("#s-Paragraph_5")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel_1" ],
                    "top": {
                      "type": "pinend",
                      "value": "-900.0"
                    },
                    "left": {
                      "type": "nomove"
                    },
                    "containment": false,
                    "effect": {
                      "type": "none",
                      "easing": "linear",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel_2" ],
                    "top": {
                      "type": "pincenter",
                      "value": "0.0"
                    },
                    "left": {
                      "type": "nomove"
                    },
                    "containment": false,
                    "effect": {
                      "type": "none",
                      "easing": "linear",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-Image_36")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimNavigation",
                  "parameter": {
                    "target": "screens/d12245cc-1680-458d-89dd-4f0d7fb22724",
                    "transition": {
                      "type": "pop",
                      "duration": 700
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-raised_Button")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-f6b09bb3-0425-43d0-84b2-39916b7a3e1a #s-raised_Button > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#3700B3"
                    }
                  } ],
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-f6b09bb3-0425-43d0-84b2-39916b7a3e1a #s-raised_Button > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#6200EE"
                    }
                  } ],
                  "exectype": "timed",
                  "delay": 200
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        },
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimNavigation",
                  "parameter": {
                    "target": "screens/2188005a-75bd-42a9-aa0c-4ce060ca089b",
                    "transition": {
                      "type": "slideright",
                      "duration": 700
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-raised_Button_1")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-f6b09bb3-0425-43d0-84b2-39916b7a3e1a #s-raised_Button_1 > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#3700B3"
                    }
                  } ],
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-f6b09bb3-0425-43d0-84b2-39916b7a3e1a #s-raised_Button_1 > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#6200EE"
                    }
                  } ],
                  "exectype": "timed",
                  "delay": 200
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        },
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimNavigation",
                  "parameter": {
                    "target": "screens/2188005a-75bd-42a9-aa0c-4ce060ca089b",
                    "transition": {
                      "type": "slideright",
                      "duration": 700
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-Image")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel_2" ],
                    "top": {
                      "type": "pinbeginning",
                      "value": "50.0"
                    },
                    "left": {
                      "type": "nomove"
                    },
                    "containment": true,
                    "effect": {
                      "type": "none",
                      "easing": "linear",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel_1" ],
                    "top": {
                      "type": "pinend",
                      "value": "0.0"
                    },
                    "left": {
                      "type": "nomove"
                    },
                    "containment": false,
                    "effect": {
                      "type": "none",
                      "easing": "linear",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-Panel_5")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel_5" ],
                    "top": {
                      "type": "nomove"
                    },
                    "left": {
                      "type": "pincenter",
                      "value": "0.0"
                    },
                    "containment": false,
                    "effect": {
                      "type": "none",
                      "easing": "swing",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-Three-line-item_6")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel_5" ],
                    "top": {
                      "type": "nomove"
                    },
                    "left": {
                      "type": "movetoposition",
                      "value": "0.0"
                    },
                    "containment": false
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-raised_Button_2")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-f6b09bb3-0425-43d0-84b2-39916b7a3e1a #s-raised_Button_2 > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#3700B3"
                    }
                  } ],
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-f6b09bb3-0425-43d0-84b2-39916b7a3e1a #s-raised_Button_2 > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#6200EE"
                    }
                  } ],
                  "exectype": "timed",
                  "delay": 200
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-raised_Button_4")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-f6b09bb3-0425-43d0-84b2-39916b7a3e1a #s-raised_Button_4 > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#3700B3"
                    }
                  } ],
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-f6b09bb3-0425-43d0-84b2-39916b7a3e1a #s-raised_Button_4 > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#6200EE"
                    }
                  } ],
                  "exectype": "timed",
                  "delay": 200
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        },
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel_5" ],
                    "top": {
                      "type": "pincenter",
                      "value": "0.0"
                    },
                    "left": {
                      "type": "pincenter",
                      "value": "0.0"
                    },
                    "containment": false,
                    "effect": {
                      "type": "none",
                      "easing": "linear",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-raised_Button_3")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-f6b09bb3-0425-43d0-84b2-39916b7a3e1a #s-raised_Button_3 > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#3700B3"
                    }
                  } ],
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-f6b09bb3-0425-43d0-84b2-39916b7a3e1a #s-raised_Button_3 > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#6200EE"
                    }
                  } ],
                  "exectype": "timed",
                  "delay": 200
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        },
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel_5" ],
                    "top": {
                      "type": "movetoposition",
                      "value": "-32.0"
                    },
                    "left": {
                      "type": "movetoposition",
                      "value": "450.0"
                    },
                    "containment": false,
                    "effect": {
                      "type": "none",
                      "easing": "swing",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-Image_34")) {
      cases = [
        {
          "blocks": [
            {
              "condition": {
                "action": "jimNot",
                "parameter": [ {
                  "datatype": "property",
                  "target": "#s-Panel_4",
                  "property": "jimIsVisible"
                } ]
              },
              "actions": [
                {
                  "action": "jimShow",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel" ]
                  },
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel" ],
                    "top": {
                      "type": "pincenter",
                      "value": "0.0"
                    },
                    "left": {
                      "type": "pincenter",
                      "value": "0.0"
                    },
                    "containment": false,
                    "effect": {
                      "type": "none",
                      "easing": "easeInQuint",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            },
            {
              "actions": [
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel" ],
                    "top": {
                      "type": "movetoposition",
                      "value": "-640.0"
                    },
                    "left": {
                      "type": "movetoposition",
                      "value": "0.0"
                    },
                    "containment": false,
                    "effect": {
                      "type": "none",
                      "easing": "easeOutCubic",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimHide",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel" ]
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    }
  })
  .on("focusin", ".s-f6b09bb3-0425-43d0-84b2-39916b7a3e1a .focusin", function(event, data) {
    var jEvent, jFirer, cases;
    if(jimUtil.isAlternateModeActive()) return;
    if(data === undefined) { data = event; }
    jEvent = jimEvent(event);
    jFirer = jEvent.getEventFirer();
    if(jFirer.is("#s-Input_4")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimShow",
                  "parameter": {
                    "target": [ "#s-Line_8" ],
                    "effect": {
                      "type": "slide",
                      "easing": "linear",
                      "duration": 200,
                      "direction": "left"
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    }
  })
  .on("focusout", ".s-f6b09bb3-0425-43d0-84b2-39916b7a3e1a .focusout", function(event, data) {
    var jEvent, jFirer, cases;
    if(jimUtil.isAlternateModeActive()) return;
    if(data === undefined) { data = event; }
    jEvent = jimEvent(event);
    jFirer = jEvent.getEventFirer();
    if(jFirer.is("#s-Input_4")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimHide",
                  "parameter": {
                    "target": [ "#s-Line_8" ]
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    }
  });	window.widgets = {
		descriptionMap : widgetDescriptionMap = {},
		rootWidgetMap : widgetRootMap = {}
	};

	widgets.descriptionMap[["s-Input_2", "f6b09bb3-0425-43d0-84b2-39916b7a3e1a"]] = ""; 

			widgets.rootWidgetMap[["s-Input_2", "f6b09bb3-0425-43d0-84b2-39916b7a3e1a"]] = ["Search bar", "s-Input_search"]; 

	widgets.descriptionMap[["s-search", "f6b09bb3-0425-43d0-84b2-39916b7a3e1a"]] = ""; 

			widgets.rootWidgetMap[["s-search", "f6b09bb3-0425-43d0-84b2-39916b7a3e1a"]] = ["Search", "s-search"]; 

	widgets.descriptionMap[["s-mic", "f6b09bb3-0425-43d0-84b2-39916b7a3e1a"]] = ""; 

			widgets.rootWidgetMap[["s-mic", "f6b09bb3-0425-43d0-84b2-39916b7a3e1a"]] = ["Mic", "s-mic"]; 

	widgets.descriptionMap[["s-Components_panel", "f6b09bb3-0425-43d0-84b2-39916b7a3e1a"]] = ""; 

			widgets.rootWidgetMap[["s-Components_panel", "f6b09bb3-0425-43d0-84b2-39916b7a3e1a"]] = ["Search bar", "s-Input_search"]; 

	widgets.descriptionMap[["s-Three-line-item", "f6b09bb3-0425-43d0-84b2-39916b7a3e1a"]] = ""; 

			widgets.rootWidgetMap[["s-Three-line-item", "f6b09bb3-0425-43d0-84b2-39916b7a3e1a"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-Three-line-item_2", "f6b09bb3-0425-43d0-84b2-39916b7a3e1a"]] = ""; 

			widgets.rootWidgetMap[["s-Three-line-item_2", "f6b09bb3-0425-43d0-84b2-39916b7a3e1a"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-Three-line-item_1", "f6b09bb3-0425-43d0-84b2-39916b7a3e1a"]] = ""; 

			widgets.rootWidgetMap[["s-Three-line-item_1", "f6b09bb3-0425-43d0-84b2-39916b7a3e1a"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-Three-line-item_3", "f6b09bb3-0425-43d0-84b2-39916b7a3e1a"]] = ""; 

			widgets.rootWidgetMap[["s-Three-line-item_3", "f6b09bb3-0425-43d0-84b2-39916b7a3e1a"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-Three-line-item_4", "f6b09bb3-0425-43d0-84b2-39916b7a3e1a"]] = ""; 

			widgets.rootWidgetMap[["s-Three-line-item_4", "f6b09bb3-0425-43d0-84b2-39916b7a3e1a"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-Three-line-item_5", "f6b09bb3-0425-43d0-84b2-39916b7a3e1a"]] = ""; 

			widgets.rootWidgetMap[["s-Three-line-item_5", "f6b09bb3-0425-43d0-84b2-39916b7a3e1a"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-Three-line-item_6", "f6b09bb3-0425-43d0-84b2-39916b7a3e1a"]] = ""; 

			widgets.rootWidgetMap[["s-Three-line-item_6", "f6b09bb3-0425-43d0-84b2-39916b7a3e1a"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-Panel_5", "f6b09bb3-0425-43d0-84b2-39916b7a3e1a"]] = ""; 

			widgets.rootWidgetMap[["s-Panel_5", "f6b09bb3-0425-43d0-84b2-39916b7a3e1a"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-raised_Button_2", "f6b09bb3-0425-43d0-84b2-39916b7a3e1a"]] = ""; 

			widgets.rootWidgetMap[["s-raised_Button_2", "f6b09bb3-0425-43d0-84b2-39916b7a3e1a"]] = ["Raised button", "s-raised_Button_2"]; 

	widgets.descriptionMap[["s-raised_Button_4", "f6b09bb3-0425-43d0-84b2-39916b7a3e1a"]] = ""; 

			widgets.rootWidgetMap[["s-raised_Button_4", "f6b09bb3-0425-43d0-84b2-39916b7a3e1a"]] = ["Raised button", "s-raised_Button_4"]; 

	widgets.descriptionMap[["s-Panel_7", "f6b09bb3-0425-43d0-84b2-39916b7a3e1a"]] = ""; 

			widgets.rootWidgetMap[["s-Panel_7", "f6b09bb3-0425-43d0-84b2-39916b7a3e1a"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-Panel_4", "f6b09bb3-0425-43d0-84b2-39916b7a3e1a"]] = ""; 

			widgets.rootWidgetMap[["s-Panel_4", "f6b09bb3-0425-43d0-84b2-39916b7a3e1a"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-raised_Button_3", "f6b09bb3-0425-43d0-84b2-39916b7a3e1a"]] = ""; 

			widgets.rootWidgetMap[["s-raised_Button_3", "f6b09bb3-0425-43d0-84b2-39916b7a3e1a"]] = ["Button", "s-raised_Button_3"]; 

	widgets.descriptionMap[["s-Image_34", "f6b09bb3-0425-43d0-84b2-39916b7a3e1a"]] = ""; 

			widgets.rootWidgetMap[["s-Image_34", "f6b09bb3-0425-43d0-84b2-39916b7a3e1a"]] = ["Help", "s-Image_34"]; 

	widgets.descriptionMap[["s-Input_1", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Input_1", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Input Text Field", "s-Input_1"]; 

	widgets.descriptionMap[["s-Paragraph_3", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Paragraph_3", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Text", "s-Paragraph_3"]; 

	widgets.descriptionMap[["s-Input", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Input", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Input Text Field", "s-Input"]; 

	widgets.descriptionMap[["s-Paragraph_4", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Paragraph_4", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Text", "s-Paragraph_4"]; 

	widgets.descriptionMap[["s-Category", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Category", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Native dropdown", "s-Category"]; 

	widgets.descriptionMap[["s-Paragraph_2", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Paragraph_2", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Text", "s-Paragraph_2"]; 

	widgets.descriptionMap[["s-Input_4", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Input_4", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Text area", "s-input_text_area"]; 

	widgets.descriptionMap[["s-Line_7", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Line_7", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Text area", "s-input_text_area"]; 

	widgets.descriptionMap[["s-Line_8", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Line_8", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Text area", "s-input_text_area"]; 

	widgets.descriptionMap[["s-Paragraph_1", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Paragraph_1", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Text", "s-Paragraph_1"]; 

	widgets.descriptionMap[["s-raised_Button", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-raised_Button", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Button", "s-raised_Button"]; 

	widgets.descriptionMap[["s-raised_Button_1", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-raised_Button_1", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Button", "s-raised_Button_1"]; 

	widgets.descriptionMap[["s-Paragraph_5", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Paragraph_5", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Paragraph", "s-Paragraph_5"]; 

	widgets.descriptionMap[["s-Input_2", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Input_2", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Search bar", "s-Input_search"]; 

	widgets.descriptionMap[["s-search", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-search", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Search", "s-search"]; 

	widgets.descriptionMap[["s-mic", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-mic", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Mic", "s-mic"]; 

	widgets.descriptionMap[["s-Components_panel", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Components_panel", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Search bar", "s-Input_search"]; 

	widgets.descriptionMap[["s-Three-line-item", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Three-line-item", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-Three-line-item_2", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Three-line-item_2", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-Three-line-item_1", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Three-line-item_1", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-Three-line-item_3", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Three-line-item_3", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-Three-line-item_4", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Three-line-item_4", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-Three-line-item_5", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Three-line-item_5", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-Three-line-item_6", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Three-line-item_6", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-Panel_5", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Panel_5", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-raised_Button_2", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-raised_Button_2", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Raised button", "s-raised_Button_2"]; 

	widgets.descriptionMap[["s-raised_Button_4", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-raised_Button_4", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Raised button", "s-raised_Button_4"]; 

	widgets.descriptionMap[["s-Panel_7", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Panel_7", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-Panel_4", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Panel_4", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-raised_Button_3", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-raised_Button_3", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Button", "s-raised_Button_3"]; 

	widgets.descriptionMap[["s-Image_34", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Image_34", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Help", "s-Image_34"]; 

	widgets.descriptionMap[["s-Paragraph", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Paragraph", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Text", "s-Paragraph"]; 

	widgets.descriptionMap[["s-Image", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Image", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Image", "s-Image"]; 

	widgets.descriptionMap[["s-Paragraph_6", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Paragraph_6", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Text", "s-Paragraph_6"]; 

	widgets.descriptionMap[["s-Image_1", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Image_1", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Image", "s-Image_1"]; 

	widgets.descriptionMap[["s-Line", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Line", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Line", "s-Line"]; 

	widgets.descriptionMap[["s-Line_1", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Line_1", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Line", "s-Line_1"]; 

	widgets.descriptionMap[["s-Line_2", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Line_2", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Line", "s-Line_2"]; 

	widgets.descriptionMap[["s-Line_3", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Line_3", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Line", "s-Line_3"]; 

	widgets.descriptionMap[["s-Line_4", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Line_4", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Line", "s-Line_4"]; 

	widgets.descriptionMap[["s-Line_5", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Line_5", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Line", "s-Line_5"]; 

	widgets.descriptionMap[["s-Paragraph_7", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Paragraph_7", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Text", "s-Paragraph_7"]; 

	widgets.descriptionMap[["s-Image_2", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Image_2", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Image", "s-Image_2"]; 

	widgets.descriptionMap[["s-Paragraph_8", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Paragraph_8", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Text", "s-Paragraph_8"]; 

	widgets.descriptionMap[["s-Image_3", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Image_3", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Image", "s-Image_3"]; 

	widgets.descriptionMap[["s-Paragraph_9", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Paragraph_9", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Text", "s-Paragraph_9"]; 

	widgets.descriptionMap[["s-Image_4", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Image_4", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Image", "s-Image_4"]; 

	widgets.descriptionMap[["s-Image_36", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ""; 

			widgets.rootWidgetMap[["s-Image_36", "979142a8-9892-4007-b3c0-314bac6bf5d3"]] = ["Home", "s-Image_36"]; 

	widgets.descriptionMap[["s-raised_Button", "2188005a-75bd-42a9-aa0c-4ce060ca089b"]] = ""; 

			widgets.rootWidgetMap[["s-raised_Button", "2188005a-75bd-42a9-aa0c-4ce060ca089b"]] = ["Button", "s-raised_Button"]; 

	widgets.descriptionMap[["s-raised_Button_1", "2188005a-75bd-42a9-aa0c-4ce060ca089b"]] = ""; 

			widgets.rootWidgetMap[["s-raised_Button_1", "2188005a-75bd-42a9-aa0c-4ce060ca089b"]] = ["Button", "s-raised_Button_1"]; 

	widgets.descriptionMap[["s-Paragraph_3", "2188005a-75bd-42a9-aa0c-4ce060ca089b"]] = ""; 

			widgets.rootWidgetMap[["s-Paragraph_3", "2188005a-75bd-42a9-aa0c-4ce060ca089b"]] = ["Text", "s-Paragraph_3"]; 

	widgets.descriptionMap[["s-Input_1", "2188005a-75bd-42a9-aa0c-4ce060ca089b"]] = ""; 

			widgets.rootWidgetMap[["s-Input_1", "2188005a-75bd-42a9-aa0c-4ce060ca089b"]] = ["Input Text Field", "s-Input_1"]; 

	widgets.descriptionMap[["s-Paragraph_4", "2188005a-75bd-42a9-aa0c-4ce060ca089b"]] = ""; 

			widgets.rootWidgetMap[["s-Paragraph_4", "2188005a-75bd-42a9-aa0c-4ce060ca089b"]] = ["Text", "s-Paragraph_4"]; 

	widgets.descriptionMap[["s-Input", "2188005a-75bd-42a9-aa0c-4ce060ca089b"]] = ""; 

			widgets.rootWidgetMap[["s-Input", "2188005a-75bd-42a9-aa0c-4ce060ca089b"]] = ["Input Text Field", "s-Input"]; 

	widgets.descriptionMap[["s-Paragraph_2", "2188005a-75bd-42a9-aa0c-4ce060ca089b"]] = ""; 

			widgets.rootWidgetMap[["s-Paragraph_2", "2188005a-75bd-42a9-aa0c-4ce060ca089b"]] = ["Text", "s-Paragraph_2"]; 

	widgets.descriptionMap[["s-Category", "2188005a-75bd-42a9-aa0c-4ce060ca089b"]] = ""; 

			widgets.rootWidgetMap[["s-Category", "2188005a-75bd-42a9-aa0c-4ce060ca089b"]] = ["Native dropdown", "s-Category"]; 

	widgets.descriptionMap[["s-Paragraph_1", "2188005a-75bd-42a9-aa0c-4ce060ca089b"]] = ""; 

			widgets.rootWidgetMap[["s-Paragraph_1", "2188005a-75bd-42a9-aa0c-4ce060ca089b"]] = ["Text", "s-Paragraph_1"]; 

	widgets.descriptionMap[["s-Input_4", "2188005a-75bd-42a9-aa0c-4ce060ca089b"]] = ""; 

			widgets.rootWidgetMap[["s-Input_4", "2188005a-75bd-42a9-aa0c-4ce060ca089b"]] = ["Text area", "s-input_text_area"]; 

	widgets.descriptionMap[["s-Line_7", "2188005a-75bd-42a9-aa0c-4ce060ca089b"]] = ""; 

			widgets.rootWidgetMap[["s-Line_7", "2188005a-75bd-42a9-aa0c-4ce060ca089b"]] = ["Text area", "s-input_text_area"]; 

	widgets.descriptionMap[["s-Line_8", "2188005a-75bd-42a9-aa0c-4ce060ca089b"]] = ""; 

			widgets.rootWidgetMap[["s-Line_8", "2188005a-75bd-42a9-aa0c-4ce060ca089b"]] = ["Text area", "s-input_text_area"]; 

	widgets.descriptionMap[["s-Paragraph", "2188005a-75bd-42a9-aa0c-4ce060ca089b"]] = ""; 

			widgets.rootWidgetMap[["s-Paragraph", "2188005a-75bd-42a9-aa0c-4ce060ca089b"]] = ["Text", "s-Paragraph"]; 

	widgets.descriptionMap[["s-Image_71", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ""; 

			widgets.rootWidgetMap[["s-Image_71", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ["Arrow Back", "s-Image_71"]; 

	widgets.descriptionMap[["s-Image_1", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ""; 

			widgets.rootWidgetMap[["s-Image_1", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ["Image", "s-Image_1"]; 

	widgets.descriptionMap[["s-Paragraph_2", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ""; 

			widgets.rootWidgetMap[["s-Paragraph_2", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ["Text", "s-Paragraph_2"]; 

	widgets.descriptionMap[["s-Panel_1", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ""; 

			widgets.rootWidgetMap[["s-Panel_1", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ["Table", "s-Table"]; 

	widgets.descriptionMap[["s-Cell", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ""; 

			widgets.rootWidgetMap[["s-Cell", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ["Table", "s-Table"]; 

	widgets.descriptionMap[["s-Image_2", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ""; 

			widgets.rootWidgetMap[["s-Image_2", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ["Image", "s-Image_2"]; 

	widgets.descriptionMap[["s-Paragraph_1", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ""; 

			widgets.rootWidgetMap[["s-Paragraph_1", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ["Text", "s-Paragraph_1"]; 

	widgets.descriptionMap[["s-Panel_3", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ""; 

			widgets.rootWidgetMap[["s-Panel_3", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ["Table", "s-Table"]; 

	widgets.descriptionMap[["s-Cell_5", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ""; 

			widgets.rootWidgetMap[["s-Cell_5", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ["Table", "s-Table"]; 

	widgets.descriptionMap[["s-Image", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ""; 

			widgets.rootWidgetMap[["s-Image", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ["Image", "s-Image"]; 

	widgets.descriptionMap[["s-Paragraph", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ""; 

			widgets.rootWidgetMap[["s-Paragraph", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ["Text", "s-Paragraph"]; 

	widgets.descriptionMap[["s-Panel_2", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ""; 

			widgets.rootWidgetMap[["s-Panel_2", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ["Table", "s-Table"]; 

	widgets.descriptionMap[["s-Cell_6", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ""; 

			widgets.rootWidgetMap[["s-Cell_6", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ["Table", "s-Table"]; 

	widgets.descriptionMap[["s-Image_42", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ""; 

			widgets.rootWidgetMap[["s-Image_42", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ["Add", "s-Image_42"]; 

	widgets.descriptionMap[["s-Cell_7", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ""; 

			widgets.rootWidgetMap[["s-Cell_7", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ["Table", "s-Table"]; 

	widgets.descriptionMap[["s-raised_Button", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ""; 

			widgets.rootWidgetMap[["s-raised_Button", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ["Button", "s-raised_Button"]; 

	widgets.descriptionMap[["s-Input_2", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ""; 

			widgets.rootWidgetMap[["s-Input_2", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ["Search bar", "s-Input_search"]; 

	widgets.descriptionMap[["s-search", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ""; 

			widgets.rootWidgetMap[["s-search", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ["Search", "s-search"]; 

	widgets.descriptionMap[["s-mic", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ""; 

			widgets.rootWidgetMap[["s-mic", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ["Mic", "s-mic"]; 

	widgets.descriptionMap[["s-Components_panel", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ""; 

			widgets.rootWidgetMap[["s-Components_panel", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ["Search bar", "s-Input_search"]; 

	widgets.descriptionMap[["s-Three-line-item", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ""; 

			widgets.rootWidgetMap[["s-Three-line-item", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-Three-line-item_2", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ""; 

			widgets.rootWidgetMap[["s-Three-line-item_2", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-Three-line-item_1", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ""; 

			widgets.rootWidgetMap[["s-Three-line-item_1", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-Three-line-item_3", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ""; 

			widgets.rootWidgetMap[["s-Three-line-item_3", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-Three-line-item_4", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ""; 

			widgets.rootWidgetMap[["s-Three-line-item_4", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-Three-line-item_5", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ""; 

			widgets.rootWidgetMap[["s-Three-line-item_5", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-Three-line-item_6", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ""; 

			widgets.rootWidgetMap[["s-Three-line-item_6", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-Panel_5", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ""; 

			widgets.rootWidgetMap[["s-Panel_5", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-raised_Button_2", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ""; 

			widgets.rootWidgetMap[["s-raised_Button_2", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ["Raised button", "s-raised_Button_2"]; 

	widgets.descriptionMap[["s-raised_Button_4", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ""; 

			widgets.rootWidgetMap[["s-raised_Button_4", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ["Raised button", "s-raised_Button_4"]; 

	widgets.descriptionMap[["s-Panel_7", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ""; 

			widgets.rootWidgetMap[["s-Panel_7", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-Panel_4", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ""; 

			widgets.rootWidgetMap[["s-Panel_4", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-raised_Button_3", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ""; 

			widgets.rootWidgetMap[["s-raised_Button_3", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ["Button", "s-raised_Button_3"]; 

	widgets.descriptionMap[["s-Image_34", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ""; 

			widgets.rootWidgetMap[["s-Image_34", "3fa7b20f-bfe1-46b3-9935-8b0b7726d103"]] = ["Help", "s-Image_34"]; 

	widgets.descriptionMap[["s-Paragraph", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ""; 

			widgets.rootWidgetMap[["s-Paragraph", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ["Text", "s-Paragraph"]; 

	widgets.descriptionMap[["s-Image", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ""; 

			widgets.rootWidgetMap[["s-Image", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ["Image", "s-Image"]; 

	widgets.descriptionMap[["s-raised_Button", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ""; 

			widgets.rootWidgetMap[["s-raised_Button", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ["Button", "s-raised_Button"]; 

	widgets.descriptionMap[["s-raised_Button_1", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ""; 

			widgets.rootWidgetMap[["s-raised_Button_1", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ["Button", "s-raised_Button_1"]; 

	widgets.descriptionMap[["s-Paragraph_1", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ""; 

			widgets.rootWidgetMap[["s-Paragraph_1", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ["Text", "s-Paragraph_1"]; 

	widgets.descriptionMap[["s-Paragraph_2", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ""; 

			widgets.rootWidgetMap[["s-Paragraph_2", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ["Text", "s-Paragraph_2"]; 

	widgets.descriptionMap[["s-Paragraph_3", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ""; 

			widgets.rootWidgetMap[["s-Paragraph_3", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ["Text", "s-Paragraph_3"]; 

	widgets.descriptionMap[["s-Input_2", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ""; 

			widgets.rootWidgetMap[["s-Input_2", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ["Search bar", "s-Input_search"]; 

	widgets.descriptionMap[["s-search", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ""; 

			widgets.rootWidgetMap[["s-search", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ["Search", "s-search"]; 

	widgets.descriptionMap[["s-mic", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ""; 

			widgets.rootWidgetMap[["s-mic", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ["Mic", "s-mic"]; 

	widgets.descriptionMap[["s-Components_panel", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ""; 

			widgets.rootWidgetMap[["s-Components_panel", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ["Search bar", "s-Input_search"]; 

	widgets.descriptionMap[["s-Three-line-item", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ""; 

			widgets.rootWidgetMap[["s-Three-line-item", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-Three-line-item_2", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ""; 

			widgets.rootWidgetMap[["s-Three-line-item_2", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-Three-line-item_1", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ""; 

			widgets.rootWidgetMap[["s-Three-line-item_1", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-Three-line-item_3", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ""; 

			widgets.rootWidgetMap[["s-Three-line-item_3", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-Three-line-item_4", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ""; 

			widgets.rootWidgetMap[["s-Three-line-item_4", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-Three-line-item_5", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ""; 

			widgets.rootWidgetMap[["s-Three-line-item_5", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-Three-line-item_6", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ""; 

			widgets.rootWidgetMap[["s-Three-line-item_6", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-Panel_5", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ""; 

			widgets.rootWidgetMap[["s-Panel_5", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-raised_Button_2", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ""; 

			widgets.rootWidgetMap[["s-raised_Button_2", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ["Raised button", "s-raised_Button_2"]; 

	widgets.descriptionMap[["s-raised_Button_4", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ""; 

			widgets.rootWidgetMap[["s-raised_Button_4", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ["Raised button", "s-raised_Button_4"]; 

	widgets.descriptionMap[["s-Panel_7", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ""; 

			widgets.rootWidgetMap[["s-Panel_7", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-Panel_4", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ""; 

			widgets.rootWidgetMap[["s-Panel_4", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ["Dynamic Panel", "s-Dynamic_Panel"]; 

	widgets.descriptionMap[["s-raised_Button_3", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ""; 

			widgets.rootWidgetMap[["s-raised_Button_3", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ["Button", "s-raised_Button_3"]; 

	widgets.descriptionMap[["s-Image_34", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ""; 

			widgets.rootWidgetMap[["s-Image_34", "d12245cc-1680-458d-89dd-4f0d7fb22724"]] = ["Help", "s-Image_34"]; 

	widgets.descriptionMap[["m-b1525292-Image_34", "b1525292-6cf3-46ec-b895-3daf05988928"]] = ""; 

			widgets.rootWidgetMap[["m-b1525292-Image_34", "b1525292-6cf3-46ec-b895-3daf05988928"]] = ["Help", "m-b1525292-Image_34"]; 

	widgets.descriptionMap[["m-b1525292-Input_1", "b1525292-6cf3-46ec-b895-3daf05988928"]] = ""; 

			widgets.rootWidgetMap[["m-b1525292-Input_1", "b1525292-6cf3-46ec-b895-3daf05988928"]] = ["Search bar", "m-b1525292-Input_search"]; 

	widgets.descriptionMap[["m-b1525292-search", "b1525292-6cf3-46ec-b895-3daf05988928"]] = ""; 

			widgets.rootWidgetMap[["m-b1525292-search", "b1525292-6cf3-46ec-b895-3daf05988928"]] = ["Search", "m-b1525292-search"]; 

	widgets.descriptionMap[["m-b1525292-mic", "b1525292-6cf3-46ec-b895-3daf05988928"]] = ""; 

			widgets.rootWidgetMap[["m-b1525292-mic", "b1525292-6cf3-46ec-b895-3daf05988928"]] = ["Mic", "m-b1525292-mic"]; 

	widgets.descriptionMap[["m-b1525292-Components_panel", "b1525292-6cf3-46ec-b895-3daf05988928"]] = ""; 

			widgets.rootWidgetMap[["m-b1525292-Components_panel", "b1525292-6cf3-46ec-b895-3daf05988928"]] = ["Search bar", "m-b1525292-Input_search"]; 

	widgets.descriptionMap[["m-b1525292-Three-line-item", "b1525292-6cf3-46ec-b895-3daf05988928"]] = ""; 

			widgets.rootWidgetMap[["m-b1525292-Three-line-item", "b1525292-6cf3-46ec-b895-3daf05988928"]] = ["Dynamic Panel", "m-b1525292-Dynamic_Panel"]; 

	widgets.descriptionMap[["m-b1525292-Three-line-item_2", "b1525292-6cf3-46ec-b895-3daf05988928"]] = ""; 

			widgets.rootWidgetMap[["m-b1525292-Three-line-item_2", "b1525292-6cf3-46ec-b895-3daf05988928"]] = ["Dynamic Panel", "m-b1525292-Dynamic_Panel"]; 

	widgets.descriptionMap[["m-b1525292-Three-line-item_1", "b1525292-6cf3-46ec-b895-3daf05988928"]] = ""; 

			widgets.rootWidgetMap[["m-b1525292-Three-line-item_1", "b1525292-6cf3-46ec-b895-3daf05988928"]] = ["Dynamic Panel", "m-b1525292-Dynamic_Panel"]; 

	widgets.descriptionMap[["m-b1525292-Three-line-item_3", "b1525292-6cf3-46ec-b895-3daf05988928"]] = ""; 

			widgets.rootWidgetMap[["m-b1525292-Three-line-item_3", "b1525292-6cf3-46ec-b895-3daf05988928"]] = ["Dynamic Panel", "m-b1525292-Dynamic_Panel"]; 

	widgets.descriptionMap[["m-b1525292-Three-line-item_4", "b1525292-6cf3-46ec-b895-3daf05988928"]] = ""; 

			widgets.rootWidgetMap[["m-b1525292-Three-line-item_4", "b1525292-6cf3-46ec-b895-3daf05988928"]] = ["Dynamic Panel", "m-b1525292-Dynamic_Panel"]; 

	widgets.descriptionMap[["m-b1525292-Three-line-item_5", "b1525292-6cf3-46ec-b895-3daf05988928"]] = ""; 

			widgets.rootWidgetMap[["m-b1525292-Three-line-item_5", "b1525292-6cf3-46ec-b895-3daf05988928"]] = ["Dynamic Panel", "m-b1525292-Dynamic_Panel"]; 

	widgets.descriptionMap[["m-b1525292-Three-line-item_6", "b1525292-6cf3-46ec-b895-3daf05988928"]] = ""; 

			widgets.rootWidgetMap[["m-b1525292-Three-line-item_6", "b1525292-6cf3-46ec-b895-3daf05988928"]] = ["Dynamic Panel", "m-b1525292-Dynamic_Panel"]; 

	widgets.descriptionMap[["m-b1525292-Panel_2", "b1525292-6cf3-46ec-b895-3daf05988928"]] = ""; 

			widgets.rootWidgetMap[["m-b1525292-Panel_2", "b1525292-6cf3-46ec-b895-3daf05988928"]] = ["Dynamic Panel", "m-b1525292-Dynamic_Panel"]; 

	widgets.descriptionMap[["m-b1525292-raised_Button", "b1525292-6cf3-46ec-b895-3daf05988928"]] = ""; 

			widgets.rootWidgetMap[["m-b1525292-raised_Button", "b1525292-6cf3-46ec-b895-3daf05988928"]] = ["Raised button", "m-b1525292-raised_Button"]; 

	widgets.descriptionMap[["m-b1525292-Panel_1", "b1525292-6cf3-46ec-b895-3daf05988928"]] = ""; 

			widgets.rootWidgetMap[["m-b1525292-Panel_1", "b1525292-6cf3-46ec-b895-3daf05988928"]] = ["Dynamic Panel", "m-b1525292-Dynamic_Panel"]; 

	widgets.descriptionMap[["m-b1525292-raised_Button_1", "b1525292-6cf3-46ec-b895-3daf05988928"]] = ""; 

			widgets.rootWidgetMap[["m-b1525292-raised_Button_1", "b1525292-6cf3-46ec-b895-3daf05988928"]] = ["Button", "m-b1525292-raised_Button_1"]; 

	(function(window, undefined) {
  var dictionary = {
    "s-f6b09bb3-0425-43d0-84b2-39916b7a3e1a s-Slice_1": [ ["Slice 1@1x.png", "338ff23e-252b-4b32-8a3f-c4e4994ddf3b_1.png"] ]
  };

  window.jimDevelopers.lookUpSlice = function(name) {
    var imageName;
    if(dictionary.hasOwnProperty(name)) { /* search by name */
      imageName = dictionary[name];
    }
    return imageName;
  };
})(window);jQuery("#simulation")
  .on("click", ".s-979142a8-9892-4007-b3c0-314bac6bf5d3 .click", function(event, data) {
    var jEvent, jFirer, cases;
    if(jimUtil.isAlternateModeActive()) return;
    if(data === undefined) { data = event; }
    jEvent = jimEvent(event);
    jFirer = jEvent.getEventFirer();
    if(jFirer.is("#s-raised_Button")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-979142a8-9892-4007-b3c0-314bac6bf5d3 #s-raised_Button > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#3700B3"
                    }
                  } ],
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-979142a8-9892-4007-b3c0-314bac6bf5d3 #s-raised_Button > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#6200EE"
                    }
                  } ],
                  "exectype": "timed",
                  "delay": 200
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        },
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimNavigation",
                  "parameter": {
                    "target": "screens/2188005a-75bd-42a9-aa0c-4ce060ca089b",
                    "transition": {
                      "type": "slideright",
                      "duration": 700
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-raised_Button_1")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-979142a8-9892-4007-b3c0-314bac6bf5d3 #s-raised_Button_1 > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#3700B3"
                    }
                  } ],
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-979142a8-9892-4007-b3c0-314bac6bf5d3 #s-raised_Button_1 > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#6200EE"
                    }
                  } ],
                  "exectype": "timed",
                  "delay": 200
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        },
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimNavigation",
                  "parameter": {
                    "target": "screens/2188005a-75bd-42a9-aa0c-4ce060ca089b",
                    "transition": {
                      "type": "slideright",
                      "duration": 700
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-Paragraph_5")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel_1" ],
                    "top": {
                      "type": "pinend",
                      "value": "-900.0"
                    },
                    "left": {
                      "type": "nomove"
                    },
                    "containment": false,
                    "effect": {
                      "type": "none",
                      "easing": "linear",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel_2" ],
                    "top": {
                      "type": "pincenter",
                      "value": "0.0"
                    },
                    "left": {
                      "type": "nomove"
                    },
                    "containment": false,
                    "effect": {
                      "type": "none",
                      "easing": "linear",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-Panel_5")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel_5" ],
                    "top": {
                      "type": "nomove"
                    },
                    "left": {
                      "type": "pincenter",
                      "value": "0.0"
                    },
                    "containment": false,
                    "effect": {
                      "type": "none",
                      "easing": "swing",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-Three-line-item_6")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel_5" ],
                    "top": {
                      "type": "nomove"
                    },
                    "left": {
                      "type": "movetoposition",
                      "value": "0.0"
                    },
                    "containment": false
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-raised_Button_2")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-979142a8-9892-4007-b3c0-314bac6bf5d3 #s-raised_Button_2 > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#3700B3"
                    }
                  } ],
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-979142a8-9892-4007-b3c0-314bac6bf5d3 #s-raised_Button_2 > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#6200EE"
                    }
                  } ],
                  "exectype": "timed",
                  "delay": 200
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-raised_Button_4")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-979142a8-9892-4007-b3c0-314bac6bf5d3 #s-raised_Button_4 > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#3700B3"
                    }
                  } ],
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-979142a8-9892-4007-b3c0-314bac6bf5d3 #s-raised_Button_4 > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#6200EE"
                    }
                  } ],
                  "exectype": "timed",
                  "delay": 200
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        },
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel_5" ],
                    "top": {
                      "type": "pincenter",
                      "value": "0.0"
                    },
                    "left": {
                      "type": "pincenter",
                      "value": "0.0"
                    },
                    "containment": false,
                    "effect": {
                      "type": "none",
                      "easing": "linear",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-raised_Button_3")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-979142a8-9892-4007-b3c0-314bac6bf5d3 #s-raised_Button_3 > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#3700B3"
                    }
                  } ],
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-979142a8-9892-4007-b3c0-314bac6bf5d3 #s-raised_Button_3 > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#6200EE"
                    }
                  } ],
                  "exectype": "timed",
                  "delay": 200
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        },
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel_5" ],
                    "top": {
                      "type": "movetoposition",
                      "value": "-32.0"
                    },
                    "left": {
                      "type": "movetoposition",
                      "value": "450.0"
                    },
                    "containment": false,
                    "effect": {
                      "type": "none",
                      "easing": "swing",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-Image_34")) {
      cases = [
        {
          "blocks": [
            {
              "condition": {
                "action": "jimNot",
                "parameter": [ {
                  "datatype": "property",
                  "target": "#s-Panel_4",
                  "property": "jimIsVisible"
                } ]
              },
              "actions": [
                {
                  "action": "jimShow",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel" ]
                  },
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel" ],
                    "top": {
                      "type": "pincenter",
                      "value": "0.0"
                    },
                    "left": {
                      "type": "pincenter",
                      "value": "0.0"
                    },
                    "containment": false,
                    "effect": {
                      "type": "none",
                      "easing": "easeInQuint",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            },
            {
              "actions": [
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel" ],
                    "top": {
                      "type": "movetoposition",
                      "value": "-640.0"
                    },
                    "left": {
                      "type": "movetoposition",
                      "value": "0.0"
                    },
                    "containment": false,
                    "effect": {
                      "type": "none",
                      "easing": "easeOutCubic",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimHide",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel" ]
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-Image")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel_2" ],
                    "top": {
                      "type": "pinbeginning",
                      "value": "50.0"
                    },
                    "left": {
                      "type": "nomove"
                    },
                    "containment": true,
                    "effect": {
                      "type": "none",
                      "easing": "linear",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel_1" ],
                    "top": {
                      "type": "pinend",
                      "value": "0.0"
                    },
                    "left": {
                      "type": "nomove"
                    },
                    "containment": false,
                    "effect": {
                      "type": "none",
                      "easing": "linear",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-Image_1")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel_7" ],
                    "top": {
                      "type": "pinbeginning",
                      "value": "50.0"
                    },
                    "left": {
                      "type": "nomove"
                    },
                    "containment": true,
                    "effect": {
                      "type": "none",
                      "easing": "linear",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel_1" ],
                    "top": {
                      "type": "pinend",
                      "value": "0.0"
                    },
                    "left": {
                      "type": "nomove"
                    },
                    "containment": false,
                    "effect": {
                      "type": "none",
                      "easing": "linear",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-Image_2")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel_8" ],
                    "top": {
                      "type": "pinbeginning",
                      "value": "50.0"
                    },
                    "left": {
                      "type": "nomove"
                    },
                    "containment": true,
                    "effect": {
                      "type": "none",
                      "easing": "linear",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel_1" ],
                    "top": {
                      "type": "pinend",
                      "value": "0.0"
                    },
                    "left": {
                      "type": "nomove"
                    },
                    "containment": false,
                    "effect": {
                      "type": "none",
                      "easing": "linear",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-Image_3")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel_9" ],
                    "top": {
                      "type": "pinbeginning",
                      "value": "50.0"
                    },
                    "left": {
                      "type": "nomove"
                    },
                    "containment": true,
                    "effect": {
                      "type": "none",
                      "easing": "linear",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel_1" ],
                    "top": {
                      "type": "pinend",
                      "value": "0.0"
                    },
                    "left": {
                      "type": "nomove"
                    },
                    "containment": false,
                    "effect": {
                      "type": "none",
                      "easing": "linear",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-Image_4")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel_10" ],
                    "top": {
                      "type": "pinbeginning",
                      "value": "50.0"
                    },
                    "left": {
                      "type": "nomove"
                    },
                    "containment": true,
                    "effect": {
                      "type": "none",
                      "easing": "linear",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel_1" ],
                    "top": {
                      "type": "pinend",
                      "value": "0.0"
                    },
                    "left": {
                      "type": "nomove"
                    },
                    "containment": false,
                    "effect": {
                      "type": "none",
                      "easing": "linear",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-Image_36")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimNavigation",
                  "parameter": {
                    "target": "screens/d12245cc-1680-458d-89dd-4f0d7fb22724",
                    "transition": {
                      "type": "pop",
                      "duration": 700
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    }
  })
  .on("focusin", ".s-979142a8-9892-4007-b3c0-314bac6bf5d3 .focusin", function(event, data) {
    var jEvent, jFirer, cases;
    if(jimUtil.isAlternateModeActive()) return;
    if(data === undefined) { data = event; }
    jEvent = jimEvent(event);
    jFirer = jEvent.getEventFirer();
    if(jFirer.is("#s-Input_4")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimShow",
                  "parameter": {
                    "target": [ "#s-Line_8" ],
                    "effect": {
                      "type": "slide",
                      "easing": "linear",
                      "duration": 200,
                      "direction": "left"
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    }
  })
  .on("focusout", ".s-979142a8-9892-4007-b3c0-314bac6bf5d3 .focusout", function(event, data) {
    var jEvent, jFirer, cases;
    if(jimUtil.isAlternateModeActive()) return;
    if(data === undefined) { data = event; }
    jEvent = jimEvent(event);
    jFirer = jEvent.getEventFirer();
    if(jFirer.is("#s-Input_4")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimHide",
                  "parameter": {
                    "target": [ "#s-Line_8" ]
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    }
  });jQuery("#simulation")
  .on("click", ".s-2188005a-75bd-42a9-aa0c-4ce060ca089b .click", function(event, data) {
    var jEvent, jFirer, cases;
    if(jimUtil.isAlternateModeActive()) return;
    if(data === undefined) { data = event; }
    jEvent = jimEvent(event);
    jFirer = jEvent.getEventFirer();
    if(jFirer.is("#s-raised_Button")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-2188005a-75bd-42a9-aa0c-4ce060ca089b #s-raised_Button > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#3700B3"
                    }
                  } ],
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-2188005a-75bd-42a9-aa0c-4ce060ca089b #s-raised_Button > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#6200EE"
                    }
                  } ],
                  "exectype": "timed",
                  "delay": 200
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        },
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimNavigation",
                  "parameter": {
                    "target": "screens/f6b09bb3-0425-43d0-84b2-39916b7a3e1a",
                    "transition": {
                      "type": "slideleft",
                      "duration": 700
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-raised_Button_1")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-2188005a-75bd-42a9-aa0c-4ce060ca089b #s-raised_Button_1 > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#3700B3"
                    }
                  } ],
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-2188005a-75bd-42a9-aa0c-4ce060ca089b #s-raised_Button_1 > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#6200EE"
                    }
                  } ],
                  "exectype": "timed",
                  "delay": 200
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        },
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimNavigation",
                  "parameter": {
                    "target": "screens/f6b09bb3-0425-43d0-84b2-39916b7a3e1a",
                    "transition": {
                      "type": "slideleft",
                      "duration": 700
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    }
  })
  .on("focusin", ".s-2188005a-75bd-42a9-aa0c-4ce060ca089b .focusin", function(event, data) {
    var jEvent, jFirer, cases;
    if(jimUtil.isAlternateModeActive()) return;
    if(data === undefined) { data = event; }
    jEvent = jimEvent(event);
    jFirer = jEvent.getEventFirer();
    if(jFirer.is("#s-Input_4")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimShow",
                  "parameter": {
                    "target": [ "#s-Line_8" ],
                    "effect": {
                      "type": "slide",
                      "easing": "linear",
                      "duration": 200,
                      "direction": "left"
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    }
  })
  .on("focusout", ".s-2188005a-75bd-42a9-aa0c-4ce060ca089b .focusout", function(event, data) {
    var jEvent, jFirer, cases;
    if(jimUtil.isAlternateModeActive()) return;
    if(data === undefined) { data = event; }
    jEvent = jimEvent(event);
    jFirer = jEvent.getEventFirer();
    if(jFirer.is("#s-Input_4")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimHide",
                  "parameter": {
                    "target": [ "#s-Line_8" ]
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    }
  });jQuery("#simulation")
  .on("click", ".s-3fa7b20f-bfe1-46b3-9935-8b0b7726d103 .click", function(event, data) {
    var jEvent, jFirer, cases;
    if(jimUtil.isAlternateModeActive()) return;
    if(data === undefined) { data = event; }
    jEvent = jimEvent(event);
    jFirer = jEvent.getEventFirer();
    if(jFirer.is("#s-Image_71")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimNavigation",
                  "parameter": {
                    "target": "screens/d12245cc-1680-458d-89dd-4f0d7fb22724",
                    "transition": {
                      "type": "turn",
                      "duration": 700
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-Cell")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimNavigation",
                  "parameter": {
                    "target": "screens/979142a8-9892-4007-b3c0-314bac6bf5d3",
                    "transition": {
                      "type": "turn",
                      "duration": 700
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-Cell_5")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimNavigation",
                  "parameter": {
                    "target": "screens/979142a8-9892-4007-b3c0-314bac6bf5d3",
                    "transition": {
                      "type": "turn",
                      "duration": 700
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-Cell_6")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimNavigation",
                  "parameter": {
                    "target": "screens/979142a8-9892-4007-b3c0-314bac6bf5d3",
                    "transition": {
                      "type": "turn",
                      "duration": 700
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-raised_Button")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-3fa7b20f-bfe1-46b3-9935-8b0b7726d103 #s-raised_Button > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#3700B3"
                    }
                  } ],
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-3fa7b20f-bfe1-46b3-9935-8b0b7726d103 #s-raised_Button > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#6200EE"
                    }
                  } ],
                  "exectype": "timed",
                  "delay": 200
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-Panel_5")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel_5" ],
                    "top": {
                      "type": "nomove"
                    },
                    "left": {
                      "type": "pincenter",
                      "value": "0.0"
                    },
                    "containment": false,
                    "effect": {
                      "type": "none",
                      "easing": "swing",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-Three-line-item_6")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel_5" ],
                    "top": {
                      "type": "nomove"
                    },
                    "left": {
                      "type": "movetoposition",
                      "value": "0.0"
                    },
                    "containment": false
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-raised_Button_2")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-3fa7b20f-bfe1-46b3-9935-8b0b7726d103 #s-raised_Button_2 > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#3700B3"
                    }
                  } ],
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-3fa7b20f-bfe1-46b3-9935-8b0b7726d103 #s-raised_Button_2 > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#6200EE"
                    }
                  } ],
                  "exectype": "timed",
                  "delay": 200
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-raised_Button_4")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-3fa7b20f-bfe1-46b3-9935-8b0b7726d103 #s-raised_Button_4 > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#3700B3"
                    }
                  } ],
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-3fa7b20f-bfe1-46b3-9935-8b0b7726d103 #s-raised_Button_4 > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#6200EE"
                    }
                  } ],
                  "exectype": "timed",
                  "delay": 200
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        },
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel_5" ],
                    "top": {
                      "type": "pincenter",
                      "value": "0.0"
                    },
                    "left": {
                      "type": "pincenter",
                      "value": "0.0"
                    },
                    "containment": false,
                    "effect": {
                      "type": "none",
                      "easing": "linear",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-raised_Button_3")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-3fa7b20f-bfe1-46b3-9935-8b0b7726d103 #s-raised_Button_3 > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#3700B3"
                    }
                  } ],
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-3fa7b20f-bfe1-46b3-9935-8b0b7726d103 #s-raised_Button_3 > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#6200EE"
                    }
                  } ],
                  "exectype": "timed",
                  "delay": 200
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        },
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel_5" ],
                    "top": {
                      "type": "movetoposition",
                      "value": "-32.0"
                    },
                    "left": {
                      "type": "movetoposition",
                      "value": "450.0"
                    },
                    "containment": false,
                    "effect": {
                      "type": "none",
                      "easing": "swing",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-Image_34")) {
      cases = [
        {
          "blocks": [
            {
              "condition": {
                "action": "jimNot",
                "parameter": [ {
                  "datatype": "property",
                  "target": "#s-Panel_4",
                  "property": "jimIsVisible"
                } ]
              },
              "actions": [
                {
                  "action": "jimShow",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel" ]
                  },
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel" ],
                    "top": {
                      "type": "pincenter",
                      "value": "0.0"
                    },
                    "left": {
                      "type": "pincenter",
                      "value": "0.0"
                    },
                    "containment": false,
                    "effect": {
                      "type": "none",
                      "easing": "easeInQuint",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            },
            {
              "actions": [
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel" ],
                    "top": {
                      "type": "movetoposition",
                      "value": "-640.0"
                    },
                    "left": {
                      "type": "movetoposition",
                      "value": "0.0"
                    },
                    "containment": false,
                    "effect": {
                      "type": "none",
                      "easing": "easeOutCubic",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimHide",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel" ]
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    }
  });jQuery("#simulation")
  .on("click", ".s-d12245cc-1680-458d-89dd-4f0d7fb22724 .click", function(event, data) {
    var jEvent, jFirer, cases;
    if(jimUtil.isAlternateModeActive()) return;
    if(data === undefined) { data = event; }
    jEvent = jimEvent(event);
    jFirer = jEvent.getEventFirer();
    if(jFirer.is("#s-raised_Button")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-d12245cc-1680-458d-89dd-4f0d7fb22724 #s-raised_Button > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#3700B3"
                    }
                  } ],
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-d12245cc-1680-458d-89dd-4f0d7fb22724 #s-raised_Button > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#6200EE"
                    }
                  } ],
                  "exectype": "timed",
                  "delay": 200
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        },
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimNavigation",
                  "parameter": {
                    "target": "screens/f6b09bb3-0425-43d0-84b2-39916b7a3e1a",
                    "transition": {
                      "type": "slidedown",
                      "duration": 700
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-raised_Button_1")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-d12245cc-1680-458d-89dd-4f0d7fb22724 #s-raised_Button_1 > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#3700B3"
                    }
                  } ],
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-d12245cc-1680-458d-89dd-4f0d7fb22724 #s-raised_Button_1 > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#6200EE"
                    }
                  } ],
                  "exectype": "timed",
                  "delay": 200
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        },
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimNavigation",
                  "parameter": {
                    "target": "screens/3fa7b20f-bfe1-46b3-9935-8b0b7726d103",
                    "transition": {
                      "type": "turn",
                      "duration": 700
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-Panel_5")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel_5" ],
                    "top": {
                      "type": "nomove"
                    },
                    "left": {
                      "type": "pincenter",
                      "value": "0.0"
                    },
                    "containment": false,
                    "effect": {
                      "type": "none",
                      "easing": "swing",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-Three-line-item_6")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel_5" ],
                    "top": {
                      "type": "nomove"
                    },
                    "left": {
                      "type": "movetoposition",
                      "value": "0.0"
                    },
                    "containment": false
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-raised_Button_2")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-d12245cc-1680-458d-89dd-4f0d7fb22724 #s-raised_Button_2 > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#3700B3"
                    }
                  } ],
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-d12245cc-1680-458d-89dd-4f0d7fb22724 #s-raised_Button_2 > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#6200EE"
                    }
                  } ],
                  "exectype": "timed",
                  "delay": 200
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-raised_Button_4")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-d12245cc-1680-458d-89dd-4f0d7fb22724 #s-raised_Button_4 > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#3700B3"
                    }
                  } ],
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-d12245cc-1680-458d-89dd-4f0d7fb22724 #s-raised_Button_4 > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#6200EE"
                    }
                  } ],
                  "exectype": "timed",
                  "delay": 200
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        },
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel_5" ],
                    "top": {
                      "type": "pincenter",
                      "value": "0.0"
                    },
                    "left": {
                      "type": "pincenter",
                      "value": "0.0"
                    },
                    "containment": false,
                    "effect": {
                      "type": "none",
                      "easing": "linear",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-raised_Button_3")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-d12245cc-1680-458d-89dd-4f0d7fb22724 #s-raised_Button_3 > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#3700B3"
                    }
                  } ],
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#s-d12245cc-1680-458d-89dd-4f0d7fb22724 #s-raised_Button_3 > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#6200EE"
                    }
                  } ],
                  "exectype": "timed",
                  "delay": 200
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        },
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel_5" ],
                    "top": {
                      "type": "movetoposition",
                      "value": "-32.0"
                    },
                    "left": {
                      "type": "movetoposition",
                      "value": "450.0"
                    },
                    "containment": false,
                    "effect": {
                      "type": "none",
                      "easing": "swing",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-Image_34")) {
      cases = [
        {
          "blocks": [
            {
              "condition": {
                "action": "jimNot",
                "parameter": [ {
                  "datatype": "property",
                  "target": "#s-Panel_4",
                  "property": "jimIsVisible"
                } ]
              },
              "actions": [
                {
                  "action": "jimShow",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel" ]
                  },
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel" ],
                    "top": {
                      "type": "pincenter",
                      "value": "0.0"
                    },
                    "left": {
                      "type": "pincenter",
                      "value": "0.0"
                    },
                    "containment": false,
                    "effect": {
                      "type": "none",
                      "easing": "easeInQuint",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            },
            {
              "actions": [
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel" ],
                    "top": {
                      "type": "movetoposition",
                      "value": "-640.0"
                    },
                    "left": {
                      "type": "movetoposition",
                      "value": "0.0"
                    },
                    "containment": false,
                    "effect": {
                      "type": "none",
                      "easing": "easeOutCubic",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimHide",
                  "parameter": {
                    "target": [ "#s-Dynamic_Panel" ]
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    }
  });jQuery("#simulation")
  .on("click", ".m-b1525292-6cf3-46ec-b895-3daf05988928 .click,.m-b1525292-6cf3-46ec-b895-3daf05988928.click", function(event, data) {
    var jEvent, jFirer, cases;
    if(jimUtil.isAlternateModeActive()) return;
    if(data === undefined) { data = event; }
    jEvent = jimEvent(event);
    jFirer = jEvent.getEventFirer();
    if(jFirer.is("#m-b1525292-Image_34")) {
      cases = [
        {
          "blocks": [
            {
              "condition": {
                "action": "jimNot",
                "parameter": [ {
                  "datatype": "property",
                  "target": "#m-b1525292-Panel_1",
                  "property": "jimIsVisible"
                } ]
              },
              "actions": [
                {
                  "action": "jimShow",
                  "parameter": {
                    "target": [ "#m-b1525292-Dynamic_Panel" ]
                  },
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#m-b1525292-Dynamic_Panel" ],
                    "top": {
                      "type": "pincenter",
                      "value": "0.0"
                    },
                    "left": {
                      "type": "pincenter",
                      "value": "0.0"
                    },
                    "containment": false,
                    "effect": {
                      "type": "none",
                      "easing": "easeInQuint",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            },
            {
              "actions": [
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#m-b1525292-Dynamic_Panel" ],
                    "top": {
                      "type": "movetoposition",
                      "value": "-640.0"
                    },
                    "left": {
                      "type": "movetoposition",
                      "value": "0.0"
                    },
                    "containment": false,
                    "effect": {
                      "type": "none",
                      "easing": "easeOutCubic",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimHide",
                  "parameter": {
                    "target": [ "#m-b1525292-Dynamic_Panel" ]
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#m-b1525292-Panel_2")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#m-b1525292-Dynamic_Panel_1" ],
                    "top": {
                      "type": "pincenter",
                      "value": "0.0"
                    },
                    "left": {
                      "type": "pincenter",
                      "value": "0.0"
                    },
                    "containment": false,
                    "effect": {
                      "type": "none",
                      "easing": "swing",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#m-b1525292-raised_Button")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#m-b1525292-6cf3-46ec-b895-3daf05988928 #m-b1525292-raised_Button > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#3700B3"
                    }
                  },{
                    "target": [ ".m-b1525292-6cf3-46ec-b895-3daf05988928 #m-b1525292-raised_Button > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#3700B3"
                    }
                  } ],
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#m-b1525292-6cf3-46ec-b895-3daf05988928 #m-b1525292-raised_Button > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#6200EE"
                    }
                  },{
                    "target": [ ".m-b1525292-6cf3-46ec-b895-3daf05988928 #m-b1525292-raised_Button > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#6200EE"
                    }
                  } ],
                  "exectype": "timed",
                  "delay": 200
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        },
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#m-b1525292-Dynamic_Panel_1" ],
                    "top": {
                      "type": "pincenter",
                      "value": "0.0"
                    },
                    "left": {
                      "type": "pincenter",
                      "value": "0.0"
                    },
                    "containment": false,
                    "effect": {
                      "type": "none",
                      "easing": "swing",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#m-b1525292-raised_Button_1")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#m-b1525292-6cf3-46ec-b895-3daf05988928 #m-b1525292-raised_Button_1 > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#3700B3"
                    }
                  },{
                    "target": [ ".m-b1525292-6cf3-46ec-b895-3daf05988928 #m-b1525292-raised_Button_1 > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#3700B3"
                    }
                  } ],
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "target": [ "#m-b1525292-6cf3-46ec-b895-3daf05988928 #m-b1525292-raised_Button_1 > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#6200EE"
                    }
                  },{
                    "target": [ ".m-b1525292-6cf3-46ec-b895-3daf05988928 #m-b1525292-raised_Button_1 > .backgroundLayer > .colorLayer" ],
                    "attributes": {
                      "background-color": "#6200EE"
                    }
                  } ],
                  "exectype": "timed",
                  "delay": 200
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        },
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimMove",
                  "parameter": {
                    "target": [ "#m-b1525292-Dynamic_Panel_1" ],
                    "top": {
                      "type": "movetoposition",
                      "value": "-32.0"
                    },
                    "left": {
                      "type": "movetoposition",
                      "value": "351.0"
                    },
                    "containment": false,
                    "effect": {
                      "type": "none",
                      "easing": "swing",
                      "duration": 500
                    }
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    }
  });