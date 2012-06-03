<#include "/layout/head.ftl">
<#include "/layout/navigation.ftl">
<div class="container">
	<div class="row-fluid">
		<#include "/account/_side.ftl">
		<div class="span8">
			<section id="account-card">
				<header><h2>Card settings</h2></header>
				<div class="card card_editor">
					<div class="card-image"><img src="/cardImage/${card.img.getKeyString()}" /></div>
					<div class="card-meta"><p>Added at ${card.created?date}</p></div>
					<hr />
					<div class="card-editor">
						<form method="POST">
							<div class="control-group">
								<label for="form-editor-name" class="control-label"><h4>Name</h4></label>
								<div class="controls">
									<input type="text" name="form-editor-name" id="form-editor-name" value="${card.name}" style="width: 98%">
								</div>
							</div>
							<div class="control-group">
								<div class="controls">
									<h4 style="margin-bottom: 6px;">Privacy</h4>
									<div class="row-fluid">
										<div class="span6">
											<label class="radio">
												<dl>
													<dt><input type="radio" value="public" name="form-editor-privacy" <#if card.privacy?? && !card.privacy>checked=""</#if> >Public</dt>
													<dd>Anyone can search for, view and add tags.</dd>
												</dl>
											</label>
										</div>
										<div class="span6">
											<label class="radio">
												<dl>
													<dt><input type="radio" value="private" name="form-editor-privacy" <#if card.privacy?? && card.privacy>checked="checked"</#if> >Private</dt>
													<dd>Only you can see, edit and add fields to this card.</dd>
												</dl>
											</label>
										</div>
									</div>
								</div>
							</div>
							<div class="form-actions">
								<button class="btn btn-primary" type="submit" name="do" value="edit">Save changes</button>
								<button class="btn btn-danger" type="submit" name="do" value="delete">Delete card</button>
							</div>
						</form>
					</div>
			</section>
			</section>
		</div>
	</div>
</div>
<#include "/layout/debug.ftl">
<#include "/layout/footer.ftl">
<#include "/layout/foot.ftl">