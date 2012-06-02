<#include "/layout/head.ftl">
<#include "/layout/navigation.ftl">
<div class="container">
	<div class="row-fluid">

		<div class="span4">
			<section id="browse-groups">
				<header><h2>My groups <a class="btn btn-mini">Manage groups</a></h2></header>
				<ul class="nav nav-tabs nav-stacked">
					{foreach $groups as $group}
					<li class="group"><a href="#">{$group['name']}<span class="badge badge-info count">{$group['count']}</span></a></li>
					{/foreach}
				</ul>
				<section id="browse-recent">
					<header><h2>Recent cards</h2></header>
					<ul>
						{foreach $listedCards as $card}<li><a href="#">{$card['name']}</a></li>{/foreach}
						{foreach $listedCards as $card}<li><a href="#">{$card['name']}</a></li>{/foreach}
						{foreach $listedCards as $card}<li><a href="#">{$card['name']}</a></li>{/foreach}
						{foreach $listedCards as $card}<li><a href="#">{$card['name']}</a></li>{/foreach}
					</ul>
				</section>
		</div>


		<div class="span8">
			<section id="browse-browser">
				<header><h2>Browsing all mine and public cards</h2></header>
				{foreach $listedCards as $card}
				<div class="card row-fluid" data-cardId="{$card['id']}">
					<div class="span6">
						<div class="card-image">
							<img src="http://placekitten.com/300/200" />
						</div>
						<div class="card-meta">
							<p class="report"><a href="#">Report</a></p>
							<p>By <a href="#">{$card['owner']}</a> at {$card['addedAt']|date:'%d.%m.%Y'}</p>
						</div>
					</div>
					<div class="span6">
						<h4 class="card-name">{$card['name']}</h4>
						<dl class="card-tags">
							{foreach $card['tags'] as $key => $val}
							<dt>{$key}</dt><dd>{$val}</dd>
							{/foreach}
						</dl>
					</div>
				</div>
				{/foreach}
				<div class="pagination">
					<ul>
						<li class="disabled"><a href="#">«</a></li>
						<li class="active"><a href="#">1</a></li>
						<li><a href="#">2</a></li>
						<li><a href="#">3</a></li>
						<li><a href="#">4</a></li>
						<li><a href="#">»</a></li>
					</ul>
				</div>
			</section>
		</div>

	</div>
</div>
<#include "/layout/debug.ftl">
<#include "/layout/footer.ftl">
<#include "/layout/foot.ftl">