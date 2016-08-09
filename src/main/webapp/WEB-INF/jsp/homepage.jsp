<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<script type='text/javascript' src='/dwr/interface/PostDwr.js'></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>

<!-- first field for selecting what kind of post to enter -->
<div class="post-container">
	<div class="post-author">
		<img src="<c:url value="/images/lightbulb.png"/>" height="64"
			width="64" alt="Placeholder Posting Author Avatar" />
	</div>
	<div class="post-content">
		<a href="<c:url value="/new/text.blr"/>">Text</a> | <a
			href="<c:url value="/new/quote.blr"/>">Quote</a> | <a
			href="<c:url value="/new/link.blr"/>">Link</a>
	</div>
</div>
<div id="posts">

	<!-- loop over the posts and print them out -->
	<c:forEach items="${postingList}" var="posting">
		<div class="post-container">
			<div class="post-author">
				<img src="<c:url value="/images/user_message.png"/>" height="64"
					width="64" alt="Posting Author" />
			</div>
			<div class="post-author">
				<c:if test="${empty post.author}">Anonymous</c:if>
				<c:if test="${not empty post.author}">${post.author}</c:if>
				:
			</div>
			<!-- check for a text post -->
			<c:if
				test='${posting.getClass().name == "com.rnsolutions.stumblr.entity.TextPost"}'>
				<!-- see if we have a title -->
				<c:if test="${not empty posting.title}">
					<div class="post-text-title">${posting.title}</div>
				</c:if>
				<!-- print the content -->
				<div class="post-text-content">${posting.text}</div>
			</c:if>
			<!-- check for a quote post -->
			<c:if
				test="${posting.getClass().name eq 'com.rnsolutions.stumblr.entity.QuotePost'}">
				<!-- print the content -->
				<div class="post-quote-quote">"${posting.quote}"</div>
				<!-- see if we have a source -->
				<c:if test="${not empty posting.source}">
					<div class="post-quote-source">${posting.source}</div>
				</c:if>
			</c:if>
			<!-- check for a link post -->
			<c:if
				test="${posting.getClass().name eq 'com.rnsolutions.stumblr.entity.LinkPost'}">
				<!-- see if we have a title -->
				<c:if test="${not empty posting.title}">
					<div class="post-link-title">${posting.title}</div>
				</c:if>
				<!-- print the content -->
				<div class="post-link-link">
					<a href="${posting.link}">${posting.link}</a>
				</div>
			</c:if>
			<hr>
		</div>
	</c:forEach>
</div>
<input type="button" value="Load More.."
	onclick="loadMore(${fn:length(postingList)})">
<script type='text/javascript'>
	function loadMore(offset) {
		PostDwr.getMorePosts(offset, {
			callback : function(str) {
				createandAddReponse(str);
			},
			errorHandler : function(message) {
				alert("Oops: " + message);
			}
		});
	}
	function createandAddReponse(str){
		
		
		var theDiv = document.getElementById("posts");
		str.forEach( function (post)
				{
			
			var iDiv = document.createElement('div');
			
			var divAuthor = document.createElement('div');
			divAuthor.class="post-author";
			var img = document.createElement('img');
			img.src="/images/user_message.png";
			img.height="64";
			img.width="64";
			img.alt="Posting Author"
			
			divAuthor.appendChild(img);
			
			var divAuthorName = document.createElement('div');
			divAuthorName.class="post-author";
			if(post.author==null){
				divAuthorName.innerHTML="Ananymous";
			}else{
				divAuthorName.innerHTML=post.author;
			}
			iDiv.class="post-container";
			iDiv.appendChild(divAuthor);
			iDiv.appendChild(divAuthorName);
			
			if(post.quote!=null){
				
				var innerDiv = document.createElement('div');
				innerDiv.class="post-quote-quote";
				innerDiv.innerHTML=post.quote;
				iDiv.appendChild(innerDiv);
				
				if(post.source!=null){
					if(post.source!=null){
						var innerDiv2 = document.createElement('div');
						innerDiv2.class="post-quote-source";
						innerDiv2.innerHTML=post.source;
						iDiv.appendChild(innerDiv2);
					}
				}
			}
				if(post.text!=null){
					var innerDiv = document.createElement('div');
					innerDiv.class="post-text-title";
					innerDiv.innerHTML=post.title;
					iDiv.appendChild(innerDiv);
					var innerDiv2 = document.createElement('div');
					innerDiv2.class="post-text-content";
					innerDiv2.innerHTML=post.text;
					iDiv.appendChild(innerDiv2);
				}
				if(post.link!=null){
					childDivText+="<div class='post-link-link'><a href='"+post.link+"''</a></div>";
					var innerDiv = document.createElement('div');
					innerDiv.class="post-link-title";
					innerDiv.innerHTML=post.title;
					iDiv.appendChild(innerDiv);
					
					var innerDiv2 = document.createElement('div');
					innerDiv2.class="post-text-content";
					var link = document.createElement('a');
					link.href=post.link;
					innerDiv2.appendChild(link);
					iDiv.appendChild(innerDiv2);
				}
			theDiv.appendChild(iDiv);
			theDiv.appendChild(document.createElement('hr'))
				});
	}
</script>