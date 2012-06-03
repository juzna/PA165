<#include "/layout/head.ftl">
<#include "/layout/navigation.ftl">
<div class="container">
	<div class="row-fluid">
		<div id="homepage-intro" class="hero-unit">
			<h1>Take your Business Cards online!</h1>
			<p>Manage your Business cards, annotate, make collections, browse public ones.</p>
		</div>
	</div>

	<div class="row-fluid">
		<div class="span8">
			<section id="homepage-activity">
				<header><h2>Recent public cards</h2></header>
				<#if (recentPublicCards)??>
					<#list recentPublicCards as card>
						<div class="card row-fluid">
							<div class="span6">
								<div class="card-image">
									<img src="/cardImage/${card.img.getKeyString()}" />
								</div>
								<div class="card-meta">
									<p>By <a href="#">${card.owner}</a> at ${card.created?date}</p>
								</div>
							</div>
							<div class="span6">
								<h4 class="card-name"><a href="/browse/card/${card.key.id?c}">${card.name!}</a></h4>
								<dl class="card-tags">
									<#list card.tags as tag>
										<dt>${tag.tagKey}</dt><dd>${tag.content}</dd>
									</#list>
								</dl>
							</div>
						</div>
					</#list>
				<#else>
					<p class="alert alert-danger">No data</p>
				</#if>
			</section>
		</div>

		<div class="span4">
			<section id="homepage-features">
				<header><h2>Features</h2></header>
				<h4>Open-source</h4>
				<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla tellus nulla, accumsan vitae congue dapibus, blandit fermentum nulla. Pellentesque tincidunt congue urna nec eleifend.</p>
				<h4>Java</h4>
				<p>Aliquam erat volutpat. Maecenas suscipit, dolor quis tristique interdum, velit purus aliquam ipsum, sit amet auctor nulla leo vel leo. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae.</p>
				<h4>Privacy</h4>
				<p>Sed vel libero eu turpis fringilla ornare sit amet quis arcu. In adipiscing molestie lorem vel egestas. Aliquam lobortis velit sit amet orci eleifend porttitor. Maecenas elementum sem at ante gravida facilisis.</p>
				<h4>Custom valie <span class="label label-info">BAM</span></h4>
				<p>Proin ut sem et arcu hendrerit placerat. Ut luctus faucibus commodo. Etiam dignissim lacus quis odio pellentesque dictum. Integer ac risus mi, sit amet volutpat nisl. Cras volutpat consequat odio, semper rhoncus urna ultrices vel.</p>
			</section>
		</div>
	</div>
</div>
<#include "/layout/debug.ftl">
<#include "/layout/footer.ftl">
<#include "/layout/foot.ftl">