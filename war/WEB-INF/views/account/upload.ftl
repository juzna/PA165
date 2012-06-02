<#include "/layout/head.ftl">
<#include "/layout/navigation.ftl">
<div class="container">
	<div class="row-fluid">
		<div class="span4">
			<section id="browse-groups">
				<header><h2>User area</h2></header>
				<ul class="nav nav-tabs nav-stacked">
					<li><a href="#">Overview</a></li>
					<li><a href="#">Manage</a></li>
					<li><a href="#">Upload</a></li>
				</ul>
			</section>
		</div>

		<div class="span8">
			<section id="account-panel">
				<header><h2>Upload new card</h2></header>
				<form class="form-horizontal" action="/account/upload/" method="POST" enctype="multipart/form-data">
					<div class="control-group">
						<label class="control-label" for="form-upload-image">Select image:</label>
						<div class="controls">
							<input class="input-file" id="form-upload-image" name="form-upload-image" type="file" />
						</div>
					</div>
					<div class="control-group">
			            <label class="control-label" for="form-upload-name">Name</label>
			            <div class="controls">
			            	<input type="text" id="form-upload-name" name="form-upload-name" />
			            </div>
			        </div>
					<div class="control-group">
						<div class="controls">
							<label class="radio">
								<dl>
									<dt><input type="radio" name="form-upload-privacy" value="public" checked="">Public</dt>
									<dd>Anyone can search for, view and add tags.</dd>
								</dl>
							</label>
							<label class="radio">
								<dl>
									<dt><input type="radio" name="form-upload-privacy" value="private" checked="">Private</dt>
									<dd>Only you can see, edit and add fields to this card.</dd>
								</dl>
							</label>
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
<#include "/layout/debug.ftl">
<#include "/layout/footer.ftl">
<#include "/layout/foot.ftl">