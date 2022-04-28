$(document).ready(function () {
    $("a[name='linkRemoveDetail']").each(function (index) {
        $(this).click(function () {
            removeDetailSectionByIndex(index);
        });
    });
});

function addNextDetailSection() {
    allDivDetails =
        $(
            "[id^='divDetail']"
        ); /** first id="divDetail0" defined in ECS-WEB004.html then id="divDetail1" id="divDetail2" ... */
    divDetailsCount = allDivDetails.length;

    htmlDetailSection = `
		<div class="form-group row" id="divDetail${divDetailsCount}">
            <label class="m-2 form-label">Name:</label>
            <input type="text" class="form-control w-25" maxlength="255" name="detailNames" />
            <label class="m-2 form-label">Value:</label>
            <input type="text" class="form-control w-25" maxlength="255" name="detailValues" />
		</div>	
	`;

    $("#divProductDetails").append(htmlDetailSection);

    previousDivDetailSection = allDivDetails.last(); // get first previous value of the last element -> For instance : there are 2 elements,
    // it only shows the icon at first one, like 3 elements , it shows the icon at 2th element.

    previousDivDetailID = previousDivDetailSection.attr("id");

    htmlLinkRemove = `
		<a class="btn fas fa-times-circle fa-2x icon-dark"
			href="javascript:removeDetailSectionById('${previousDivDetailID}')"
			title="Remove this detail"></a>
	`;

    previousDivDetailSection.append(htmlLinkRemove);

    $("input[name='detailNames']").last().focus();
}

function removeDetailSectionById(id) {
    $("#" + id).remove();
}

function removeDetailSectionByIndex(index) {
    $("#divDetail" + index).remove();
}