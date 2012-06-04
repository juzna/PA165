<#include "/layout/head.ftl">
<#include "/layout/navigation.ftl">
<div class="container">
	<div class="row-fluid">
		<#include "/account/_side.ftl">
		<div class="span8">
			<section id="account-panel">
				<header><h2>Upload new card</h2></header>
				<form class="form-horizontal" action="/account/upload/" method="POST" enctype="multipart/form-data">
					<div class="control-group">
						<label class="control-label" for="form-upload-image">Select image:</label>
						<div class="controls">
							<input class="input-file" id="form-upload-image" name="form-upload-image" type="file" onchange="photoChanged(this.files)" />
						</div>
						<div id="PhotoPreviewWrapper" style="overflow: hidden; width: 400px; margin-bottom: 5px; display: none;">
							<strong>Preview</strong>
							<img id="PhotoPreview" alt="Photo" src="http://placehold.it/220x120" alt="Preview" />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="form-upload-name">Name</label>
						<div class="controls">
							<input type="text" id="form-upload-name" name="form-upload-name" style="width: 98%" />
						</div>
					</div>
					<div class="control-group margined">
						<div class="controls">
							<h4 style="margin-bottom: 6px;">Privacy</h4>
							<div class="row-fluid">
								<div class="span6">
									<label class="radio">
										<dl>
											<dt><input type="radio" value="public" name="form-upload-privacy" checked="checked">Public</dt>
											<dd>Anyone can search for, view and add tags.</dd>
										</dl>
									</label>
								</div>
								<div class="span6">
									<label class="radio">
										<dl>
											<dt><input type="radio" value="private" name="form-upload-privacy">Private</dt>
											<dd>Only you can see, edit and add fields to this card.</dd>
										</dl>
									</label>
								</div>
							</div>
						</div>
					</div>
					<div class="form-actions">
						<button type="submit" class="btn btn-primary">Upload a redirect to card</button>
						<button class="btn">Cancel</button>
					</div>
				</form>
			</section>
		</div>
	</div>
</div>
<script type="text/javascript">
	window.URL = window.URL || window.webkitURL || window.mozURL;
	function photoChanged(files) {
		if (!files || !files.length) return;
		var file = files[0];
		if (file.type != 'image/jpeg' /*&& file.type != 'image/png'*/) {
			alert('The photo must be in JPEG format');
			$("#form-upload-image").val('');
			return;
		}
		if (typeof window.URL == 'undefined') return;
		$('#PhotoPreview').attr('src', window.URL.createObjectURL(file));
		$('#PhotoPreviewWrapper').show();
	}
</script>
<#include "/layout/debug.ftl">
<#include "/layout/footer.ftl">
<#include "/layout/foot.ftl">
