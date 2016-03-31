(function () {
    /* Imports */
    var $j = this.JpegMeta.JpegFile;

    /* Implementation */
    function $(x) {
        return document.getElementById(x);
    }

    function dragEnterHandler(e) {
	e.preventDefault();
    }

    function dragOverHandler(e) {
	e.preventDefault();
    }

    function dropHandler(e) {
	e.preventDefault();
	loadFiles(e.dataTransfer.files);
    }

    function strComp(a, b) {
	return (a > b) ? 1 : (a == b) ? 0 : -1;
    }

    function loadFiles(files) {
	var dataurl_reader = new FileReader();

	function display(data, filename) {
	    var jpeg = new $j(data, filename);
	    var groups = new Array;
	    var props;
	    var group;
	    var prop;

	    if (jpeg.gps && jpeg.gps.longitude) {
		$("data").innerHTML= "<input type='hidden' id='lat' name='lat' value='"+jpeg.gps.latitude.value+"'>";
		$("data").innerHTML+= "<input type='hidden' id='lng' name='lng' value='"+jpeg.gps.longitude.value+"'>";
		initialize(jpeg.gps.latitude.value, jpeg.gps.longitude.value)
	    }

	    for (group in jpeg.metaGroups) {
                if (jpeg.metaGroups.hasOwnProperty(group)) {
		    groups.push(jpeg.metaGroups[group]);
                }
	    }

	    groups.sort(function (a, b) {
		if (a.description == "General") {
		    return -1;
		} else if (b.description == "General") {
		    return 1;
		} else {
		    return strComp(a.description, b.description);
		}
	    });

	    for (var i = 0; i < groups.length; i++) {
                group = groups[i];
		props = new Array();
		for (prop in group.metaProps) {
                    if (group.metaProps.hasOwnProperty(prop)) {
		        props.push(group.metaProps[prop]);
                    }
		}
		props.sort(function (a, b) { return strComp(a.description, b.description); });
		for (var j = 0; j < props.length; j++) {
                    prop = props[j];
		}
	    }
	}

	dataurl_reader.onloadend = function() {
            display(atob(this.result.replace(/^.*?,/,'')), files[0]);
	}

	dataurl_reader.readAsDataURL(files[0]);
    }

    window.onload = function() {
	var file_el = $("file");
	file_el.addEventListener("change", function() { loadFiles(this.files); }, true);
    }
    /* No exports */
})();
