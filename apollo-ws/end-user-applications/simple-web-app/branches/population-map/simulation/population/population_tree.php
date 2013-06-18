<html><head><title>Population Initialization</title>

        <script type="text/javascript">
            function toggleInstructions(id) {
                var e = $("#" + id);
                var text = $("#show-hide-instruction-text");
                if(e.css("display") == 'block') {
                    e.css("display", "none");
                    text.text("Show instructions (+)");
                }
                else {
                    e.css("display", "block");
                    text.text("Hide instructions (-)");
                }
            }
        </script>
    </head>
    <body>

        <table cellpadding="12" style="width: 100%;">
            <tr>
                <td style="width: 350px; background-color: #E0F0FF; border: 1px solid #B8B8B8; text-align: left; vertical-align: top">
                    <div style="overflow:auto; height: 600px">

                        <button id="store-fractions" onclick="storeSelectedRegionFractions();" style="width: 250px; height: 40px; font-size: 12px; border: 1px solid #99cccc">
                            <span class="ui-button-text">Store fractions for selected regions</span>
                        </button>

                        <ul id="population-tree" class="treeview" style="text-align: left; padding: 0px">

                        </ul>
                    </div>
                </td>
                <td style="vertical-align: top">
                    <h2>Population Initialization</h2>
                    <a onclick="toggleInstructions('population-instructions')" id="show-hide-instruction-text" style="cursor: hand; cursor: pointer">Show instructions (+)</a>
                    <br/>
                    <div style="overflow: auto; height: 500px">
                        <div id="population-instructions" style="display: none">
                            <br/>
                            Use the tree on the left to specify how the simulated population will be initialized by selecting locations and 
                            initial disease fractions. 
                            The population will be initialized from the tree according to the following rules:
                            <ul>
                                <li>The top-most region selected will be the entire region of the epidemic model</li>
                                <li>Any selected sub-region will be used as an exception to selected regions containing it</li>
                            </ul>
                            As an example, say "United States" is selected with "Fraction infected: 0.1", "Alabama" is selected with "Fraction infected: 0.05", 
                            and "Baldwin County" is selected with "Fraction infected: 0.2". This means that the epidemic model is for the entire
                            United States, and everywhere except the state of Alabama will have an initial infected fraction of 0.1. In Alabama,
                            everywhere except Baldwin county will have an initial infected fraction of 0.05. In Baldwin county, the initial infected
                            fraction will be 0.2.
                        </div>
                        <br/>

                        <div id="map"></div>
                    </div>
                </td>


            </tr>
        </table>
        <!--        <a href="javascript:ddtreemenu.flatten('population-tree', 'expand')">Expand All</a> | <a href="javascript:ddtreemenu.flatten('population-tree', 'contact')">Contact All</a>-->



        <script type="text/javascript">

            var html = createPopulationHtmlTree();
            $("#population-tree").html(html);
            
            restoreSelectedRegions();
            restoreSelectedFractions();

            //ddtreemenu.createTree(treeid, enablepersist, opt_persist_in_days (default is 1))

            //            ddtreemenu.createTree("treemenu1", true)
            ddtreemenu.createTree("population-tree", false);
            
            $('#store-fractions').button({
                disabled : false //enable until everything is ready
            });
            
            //        ddtreemenu.expandSubTree("population-tree", document.getElementById("01003"));

        </script>

        <script type="text/javascript" src="js/mapseir.js"></script>

        <script type="text/javascript">
            loadStoredFractionsInMap();
        </script>

    </body>
</html>
