<#include "/layout/head.ftl">
<#include "/layout/navigation.ftl">
<div class="container">
	<div class="row-fluid" style="margin-bottom: -30px;">
		<a class="btn btn" href="#">« Back</a>
	</div>

	<section id="browse-card-billboard" class="row-fluid">
		<div class="span12">
			<div class="card-image"><img src="http://placekitten.com/940/620" /></div>
			<div class="card-meta">
				<p class="report"><a href="#">Report</a></p>
				<p>By <a href="#">{$card['owner']}</a> at {$card['addedAt']|date:'%d.%m.%Y'}</p>
			</div>
		</div>
	</section>

	<div class="row-fluid margined">
		<div class="span6">
			<div class="card" style="margin-top: 0px;">
				<h4 class="card-name">{$card['name']}</h4>
				<dl class="card-tags">
					{foreach $card['tags'] as $key => $val}
					<dt>{$key}</dt><dd>{$val}</dd>
					{/foreach}
				</dl>
				<form class="tagger margined">
					<input class="tagger-key" type="text" placeholder="Key">
					<label class="tagger-private"><input type="checkbox" class="checkbox"> Private</label>
					<input class="tagger-value" type="text" placeholder="Value">
					<button class="tagger-submit btn" type="submit" >Add tag</button>
				</form>
			</div>
		</div>

		<div class="span6">
			<section id="browse-card-belongs">
				<header><h3>Groups containing this card <a class="btn btn-mini">Add to group</a></h3></header>
				<ul class="nav nav-tabs nav-stacked">
					{foreach $relatedGroups as $group}
					{if $iterator->odd}
					<li class="group"><a href="#">{$group['name']}<span class="badge badge-info count">{$group['count']}</span></a></li>
					{/if}
					{/foreach}
				</ul>
			</section>
			<section id="browse-card-ownership" class="marginer">
				<p class="alert alert-info">This card belongs to you. <a class="btn btn-primary">Manage this card</a></p>
			</section>
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