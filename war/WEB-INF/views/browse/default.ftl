<#include "/layout/head.ftl">
<#include "/layout/navigation.ftl">
<div class="container">
	<div class="row-fluid">

		<div class="span4">
			<section id="browse-groups">
				<header><h2>My groups <a class="btn btn-mini">Manage groups</a></h2></header>
				<#if myGroups??>
					<ul class="nav nav-tabs nav-stacked">
						<#list myGroups as group>
							<li class="group"><a href="#">${group.name}<span class="badge badge-info count">TODO</span></a></li>
						</#list>
					</ul>
				<#else>
					<p class="well">Auth area</p>
				</#if>
			</section>
				
			<section id="browse-recent">
				<header><h2>Recent cards</h2></header>
				<#if recentPublicCards??>
					<ul>
						<#list recentPublicCards as card>
							<li><a href="/browse/card/${card.key}">${card.name}</a></li>
						</#list>
					</ul>
				<#else>
					<p class="well">No data</p>
				</#if>
			</section>
		</div>


		<div class="span8">
			<section id="browse-browser">
				<header><h2>Browsing all mine and public cards</h2></header>
				<#if cards??>
					<#list cards as card>
						<div class="card row-fluid">
							<div class="span6">
								<div class="card-image">
									<img src="/cardImage/${card.img.getKeyString()}" />
								</div>
								<div class="card-meta">
									<p>By <a href="/browse/user/${card.owner}/">${card.owner}</a> at ${card.created?date}</p>
								</div>
							</div>
							<div class="span6">
								<h4 class="card-name"><a href="/browse/card/${card.key.id}/">${card.name!}</a></h4>
								<dl class="card-tags">
									<#list card.tags as tag>
										<dt>${tag.tagKey}</dt><dd>${tag.content}</dd>
									</#list>
								</dl>
							</div>
						</div>
					</#list>
				<#else>
					<p class="well">No data</p>
				</#if>
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