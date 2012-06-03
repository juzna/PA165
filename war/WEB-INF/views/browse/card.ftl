<#include "/layout/head.ftl">
<#include "/layout/navigation.ftl">
<div class="container">
	<div class="row-fluid" style="margin-bottom: -30px;">
		<a class="btn btn" href="/browse/">« Back to Browse</a>
	</div>

	<section id="browse-card-billboard" class="row-fluid">
		<div class="span12">
			<div class="card-image"><img src="/cardImage/${card.img.getKeyString()}" /></div>
			<div class="card-meta">
				<p>By <a href="/browse/user/${card.owner}/">${card.owner}</a> at ${card.created?date}</p>
			</div>
		</div>
	</section>

	<div class="row-fluid margined">
		<div class="span6">
			<div class="card" style="margin-top: 0px;">
				<h4 class="card-name">${card.name}</h4>
				<#if card.tags??>
					<dl class="card-tags">
						<#list card.tags as tag>
							<dt>${tag.tagKey!"Undefined"}</dt><dd>${tag.content!"Undefined"}</dd>
						</#list>
					</dl>
				<#else>
					<p class="well">No data</p>
				</#if>
				<form class="tagger margined" method="POST">
					<input type="hidden" name="do" value="addTag" />
					<input class="tagger-key" name="tagger-key" type="text" placeholder="Key">
					<label class="tagger-private" name="tagger-private"><input type="checkbox" class="checkbox"> Private</label>
					<input class="tagger-value" name="tagger-value" type="text" placeholder="Value">
					<button class="tagger-submit btn" type="submit" >Add tag</button>
				</form>
			</div>
		</div>

		<div class="span6">
			<#if card.owner == user>
				<section id="browse-card-ownership" class="marginer">
					<p class="alert alert-info">This card belongs to you. <a href="/account/card/${card.key.id?c}/"><strong>Manage this card</strong></a></p>
				</section>
			</#if>
		
			<#if user??>
				<section id="browse-card-belongs">
					<header><h3>Groups containing this card</h3></header>
					<#if groupsOfCard??>
					<ul class="nav nav-tabs nav-stacked">
						<#list groupsOfCard as group>
							<li class="group">
								<form method="POST" class="form-delete">
									<input type="hidden" name="do" value="removeFromGroup" />
									<input type="hidden" name="groupId" value="${group.key.id?c}" />
									<button type="submit" class="btn btn-mini">Remove</button>
								</form>
								<a href="/browse/?group=${group.key.id?c}">${group.name}</a>
							</li>
						</#list>
					</ul>
					<#else>
						<p class="well">No data</p>
					</#if>
				</section>
			<#else>
				<p class="well">Auth area</p>
			</#if>
			
			<#if user??>
				<section id="browse-card-addToGroup">
				<header><h4>Add card to group</h4></header>
					<form method="POST" class="form-horizontal">
						<input type="hidden" name="do" value="addToGroup" />
						<select name="grouper-id">
							<#if allUsersGroups??>
								<option value="">Select group...</option>
								<#list allUsersGroups as group>
									<option value="${group.key.id?c}">${group.name}</option>
								</#list>
							<#else>
								<option value="">You have no groups</options>
							</#if>
						</select>
						<button class="btn" type="submit" >Add to existing group</button>
					</form>
					
					<form method="POST" class="form-horizontal">
						<input type="hidden" name="do" value="addToGroup" />
						<input name="grouper-name" type="text" placeholder="Group name">
						<button class="btn" type="submit" >Add to new group</button>
					</form>
				</section>
			</#if>
			
		</div>
	</div>
</div>
<#include "/layout/debug.ftl">
<#include "/layout/footer.ftl">
<#include "/layout/foot.ftl">