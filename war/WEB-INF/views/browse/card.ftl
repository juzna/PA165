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
			<#if user??>
				<section id="browse-card-belongs">
					<header><h3>Groups containing this card <a class="btn btn-mini">Add to group</a></h3></header>
					<#if groupsOfCard??>
					<ul class="nav nav-tabs nav-stacked">
						<#list groupsOfCard as group>
							<li class="group"><a href="/browse/group/${group.id}/">${group.name}</a></li>
						</#list>
					</ul>
					<#else>
						<p class="well">No data</p>
					</#if>
				</section>
			<#else>
				<p class="well">Auth area</p>
			</#if>
			
			<#if card.owner == user>
				<section id="browse-card-ownership" class="marginer">
					<p class="alert alert-info">This card belongs to you. <a class="btn btn-primary">Manage this card</a></p>
				</section>
			</#if>
			
			<#if user??>
				<section id="browse-card-addToGroupTemp">
				<header><h4>Add to your group</h4></header>
					<ul>
					<#if allUsersGroups??>
						<#list allUsersGroups as group>
							<li class="group"><a href="#">${group.name}</a></li>
						</#list>
					<#else>
						<p class="well">No data</p>
					</#if>
					</ul>
					<form method="POST">
						<input type="hidden" name="do" value="addToGroup" />
						<input class="grouper-name" name="grouper-name" type="text" placeholder="Group name">
						<button class="grouper-submit btn" type="submit" >Add group</button>
					</form>
				</section>
			</#if>
			
		</div>
	</div>

	<section id="browse-related">
		<header><h2>Related cards <small>You may also like.</small></h2></header>
		<div class="row-fluid">
			{foreach $relatedCards as $card}
			<div class="card card_thumb span4" data-cardId="{$card['id']}" {if ($iterator->counter % 3)  == 1}style="margin-left: 0;"{/if}>
			<div class="card-image">
				<img src="http://placekitten.com/300/200" />
			</div>
			<h4 class="card-name">{$card['name']}</h4>
		</div>
		{/foreach}
</div>
</section>
</div>
<#include "/layout/debug.ftl">
<#include "/layout/footer.ftl">
<#include "/layout/foot.ftl">