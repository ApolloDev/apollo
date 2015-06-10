$(document)
		.ready(
				function() {
						
					
				
					$('.intercept')
							.click(
									function(e) {
										e.preventDefault();

										var webpageName = $(this).attr(
												"data-webpageName");

										if (webpageName == "individualPage") {

											if ($(this).attr("id") == 'version-diffs') {
												var urn = $(this).attr(
														'data-uri');
												var formData = {
													uri : urn
												};

												var urlSections = window.location.pathname
														.split("/");
												var urlForView = "/"
														+ urlSections[1]
														+ "/versionDiff";

												$
														.ajax({
															url : urlForView,
															type : "GET",
															data : formData,
															success : function(
																	data) {
																console
																		.log("in diff");
																$('#modal-div')
																		.empty();
																$('#modal-div')
																		.append(
																				'<button class="close-reveal-modal">&#215;</button>');
																$('#modal-div')
																		.append(
																				data);

															},
														});

											} else if ($(this).attr("id") == 'set-version') {
												console.log('in version');
												var urn = $(this).attr(
														'data-uri');
												var formData = {
													uri : urn
												};

												var urlSections = window.location.pathname
														.split("/");

												var urlForView = "/"
														+ urlSections[1]
														+ "/releaseVersion";
												$
														.ajax({
															url : urlForView,
															type : "GET",
															data : formData,
															success : function(
																	data) {
																$('#modal-div')
																		.empty();
																$('#modal-div')
																		.append(
																				'<button class="close-reveal-modal">&#215;</button>');
																$('#modal-div')
																		.append(
																				data);

															},
														});
											} else {

												var link = $(this).attr("href");
												$('#modal-div').empty();
												$('#modal-div')
														.append(
																'<button class="close-reveal-modal">&#215;</button>');
												$('#modal-div')
														.append(
																'<h4 class="section_header lbl">Reference:</h4>');
												$
														.get(
																link,
																function(
																		pagecontents) {
																	var result = $(pagecontents);
																	$(
																			'#modal-div')
																			.append(
																					'<div id="data-inside-modal-div"></div>');
																	$(link)
																			.clone()
																			.appendTo(
																					$('#data-inside-modal-div'));

																});
											}
										} else {
											var link = $(this).attr("href");
											var epidemicName = $(this).attr(
													"data-epidemicName");

											var location = $(this).attr(
													"data-location");

											var theTrees = $(location).html();
											$('#modal-div').empty();
											$('#modal-div')
													.append(
															'<button class="close-reveal-modal">&#215;</button>');
											if (location == "#tree") {
												$('#modal-div')
														.append(
																'<br><h4 class="summary-tree-title">'
																		+ epidemicName
																		+ '</h4><br><br>');
												/*
												 * $('#modal-div').append($('.summary-trees[description="'+
												 * epidemicName));
												 */
												var treeDivToDisplay = $(
														'.epidemicTreeGroups[data-description="'
																+ epidemicName
																+ '"]').html();
												$('#modal-div')
														.append(
																'<div id="data-inside-modal-div"></div>');
												$(
														'.epidemicTreeGroups[data-description="'
																+ epidemicName
																+ '"]')
														.clone()
														.appendTo(
																$('#data-inside-modal-div'));
												/* $('.epidemicTreeGroups[data-description="'+epidemicName+'"]').clone().appendTo($('#modal-div')); */

											} else {

												$
														.get(
																link,
																function(
																		pagecontents) {
																	var result = $(pagecontents);
																	$(
																			'#modal-div')
																			.append(
																					"<h4>"
																							+ epidemicName
																							+ ":</h4>");

																	$(
																			'#modal-div')
																			.append(
																					'<div id="data-inside-modal-div" class="modal-div-data-indent"></div>');
																	$(
																			'#modal-div')
																			.append(
																					'<div id="display-references-toggle" class="modal-div-data-indent"></div>');
																	$(result)
																			.find(
																					location)
																			.appendTo(
																					'#data-inside-modal-div');
																	$(
																			'#modal-div')
																			.append(
																					'<div id="references-inside-modal-div" class="modal-div-data-indent"></div>');

																	if (location != "#map") {
																		/*
																		 * $('#modal-div').append('<button
																		 * id="hide"
																		 * class="modal-reference-option">Hide
																		 * references</span>');
																		 */

																		$(
																				'#display-references-toggle')
																				.append(
																						'<button id="show" class="modal-reference-option">Show references</span>');

																		$(
																				'#display-references-toggle')
																				.append(
																						'<button id="hide" class="modal-reference-option">Hide references</span>');
																		$(
																				'#hide')
																				.hide();

																		$(
																				'#show')
																				.click(
																						function(
																								e) {

																							$(
																									'#ref_for_summary')
																									.show();
																							$(
																									'#show')
																									.hide();
																							$(
																									'#hide')
																									.show();

																						});
																		$(
																				'#hide')
																				.click(
																						function(
																								e) {

																							$(
																									'#ref_for_summary')
																									.hide();
																							$(
																									'#hide')
																									.hide();
																							$(
																									'#show')
																									.show();

																						});

																		$(
																				result)
																				.find(
																						"#ref_for_summary")
																				.appendTo(
																						'#references-inside-modal-div');
																		$(
																				"#ref_for_summary")
																				.hide();

																		$(
																				'#modal-div')
																				.find(
																						'a')
																				.each(
																						function(
																								index) {

																							if ($(
																									this)
																									.attr(
																											"href")[0] == '#') {
																								$(
																										this)
																										.click(
																												function(
																														e) {
																													/*
																													 * this.href =
																													 * '#modal-div';
																													 */

																													$(
																															"#ref_for_summary")
																															.show();
																													$(
																															'#show')
																															.hide();
																													$(
																															'#hide')
																															.show();
																													var elem = $(
																															this)
																															.attr(
																																	"href");

																													var docViewTop = $(
																															window)
																															.scrollTop();
																													var docViewBottom = docViewTop
																															+ $(
																																	window)
																																	.height();

																													var elemTop = $(
																															elem)
																															.offset().top;
																													var elemBottom = elemTop
																															+ $(
																																	elem)
																																	.height();

																													if (elemBottom <= docViewBottom) {
																														return false;
																													} else {

																														this.href = '#references-inside-modal-div';
																													}
																												});
																							}
																							;

																						});

																	}

																}, 'html');
											}
										}
										setReveal();
										$('#modal-div').reveal($(this).data());

									});
					
				});

/*---------------------------
Extend and Execute
----------------------------*/

function setReveal() {
	$.fn.reveal = function(options) {

		var defaults = {
			animation : 'fadeAndPop', // fade, fadeAndPop,
			// none
			animationspeed : 300, // how fast animtions
			// are
			closeonbackgroundclick : true, // if you click
			// background will
			// modal close?
			dismissmodalclass : 'close-reveal-modal' // the
		// class
		// of a
		// button
		// or
		// element
		// that
		// will
		// close
		// an
		// open
		// modal
		};

		// Extend dem' options
		var options = $.extend({}, defaults, options);

		return this
				.each(function() {

					/*---------------------------
					 Global Variables
					 ----------------------------*/
					var modal = $(this), topMeasure = parseInt(modal
							.css('top')), topOffset = modal
							.height()
							+ topMeasure, locked = false, modalBG = $('.reveal-modal-bg');

					/*---------------------------
					 Create Modal BG
					 ----------------------------*/
					if (modalBG.length == 0) {
						modalBG = $(
								'<div class="reveal-modal-bg" />')
								.insertAfter(modal);
					}

					/*---------------------------
					 Open & Close Animations
					 ----------------------------*/
					// Entrance Animations
					modal
							.bind(
									'reveal:open',
									function() {
										modalBG
												.unbind('click.modalEvent');
										$(
												'.'
														+ options.dismissmodalclass)
												.unbind(
														'click.modalEvent');
										if (!locked) {
											lockModal();
											if (options.animation == "fadeAndPop") {
												modal
														.css({
															'top' : $(
																	document)
																	.scrollTop()
																	- topOffset,
															'opacity' : 0,
															'visibility' : 'visible'
														});
												modalBG
														.fadeIn(options.animationspeed / 2);
												modal
														.delay(
																options.animationspeed / 2)
														.animate(
																{
																	"top" : $(
																			document)
																			.scrollTop()
																			+ topMeasure
																			+ 'px',
																	"opacity" : 1
																},
																options.animationspeed,
																unlockModal());
											}
											if (options.animation == "fade") {
												modal
														.css({
															'opacity' : 0,
															'visibility' : 'visible',
															'top' : $(
																	document)
																	.scrollTop()
																	+ topMeasure
														});
												modalBG
														.fadeIn(options.animationspeed / 2);
												modal
														.delay(
																options.animationspeed / 2)
														.animate(
																{
																	"opacity" : 1
																},
																options.animationspeed,
																unlockModal());
											}
											if (options.animation == "none") {
												modal
														.css({
															'visibility' : 'visible',
															'top' : $(
																	document)
																	.scrollTop()
																	+ topMeasure
														});
												modalBG
														.css({
															"display" : "block"
														});
												unlockModal()
											}
										}
										modal
												.unbind('reveal:open');
									});

					// Closing Animation
					modal
							.bind(
									'reveal:close',
									function() {
										$('#modal-div')
												.empty();
										if (!locked) {
											lockModal();
											if (options.animation == "fadeAndPop") {
												modalBG
														.delay(
																options.animationspeed)
														.fadeOut(
																options.animationspeed);
												modal
														.animate(
																{
																	"top" : $(
																			document)
																			.scrollTop()
																			- topOffset
																			+ 'px',
																	"opacity" : 0
																},
																options.animationspeed / 2,
																function() {
																	modal
																			.css({
																				'top' : topMeasure,
																				'opacity' : 1,
																				'visibility' : 'hidden'
																			});
																	unlockModal();
																});
											}
											if (options.animation == "fade") {
												modalBG
														.delay(
																options.animationspeed)
														.fadeOut(
																options.animationspeed);
												modal
														.animate(
																{
																	"opacity" : 0
																},
																options.animationspeed,
																function() {
																	modal
																			.css({
																				'opacity' : 1,
																				'visibility' : 'hidden',
																				'top' : topMeasure
																			});
																	unlockModal();
																});
											}
											if (options.animation == "none") {
												modal
														.css({
															'visibility' : 'hidden',
															'top' : topMeasure
														});
												modalBG
														.css({
															'display' : 'none'
														});
											}
										}

										modal
												.unbind('reveal:close');
									});

					/*---------------------------
					 Open and add Closing Listeners
					 ----------------------------*/
					// Open Modal Immediately
					modal.trigger('reveal:open')

					// Close Modal Listeners
					var closeButton = $(
							'.' + options.dismissmodalclass)
							.bind(
									'click.modalEvent',
									function() {
										modal
												.trigger('reveal:close')
									});

					if (options.closeonbackgroundclick) {
						modalBG.css({
							"cursor" : "pointer"
						})
						modalBG
								.bind(
										'click.modalEvent',
										function() {
											modal
													.trigger('reveal:close')
										});
					}
					$('body').keyup(function(e) {
						if (e.which === 27) {
							modal.trigger('reveal:close');
						} // 27 is the keycode for the
						// Escape
						// key
					});

					/*---------------------------
					 Animations Locks
					 ----------------------------*/
					function unlockModal() {
						locked = false;
					}
					function lockModal() {
						locked = true;
					}

				});
	};

}
;