<%@page import="java.net.URLEncoder"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/mkoenig-html.tld" prefix="html_mk"%>
<%@ taglib uri="/WEB-INF/tld/mkoenig-logic.tld" prefix="logic_mk"%>
<%@page import="java.util.*"%>
<%@page import="de.tw.common.*"%>
<%@page import="de.tw.common.enumeration.*"%>
<%@page import="de.tw.ejb.*"%>
<%@page import="de.tw.web.struts.shop.form.*"%>
<%@page import="de.mkoenig.navigation.I_NavigationHandler"%>
<%@page import="de.mkoenig.taglib.URLWriterTag"%>
<%@page import="de.mkoenig.tools.configproperties.FileConfigProperties"%>
<%@page import="de.mkoenig.tools.Utilities"%>
<%@page import="de.mkoenig.tools.MK_SortedList"%>
<%@page import="de.tw.common.comparator.Comparator_Categories_BY_RANK"%>
<%@page import="de.mkoenig.tools.sort.ReflectionSorter"%>
<%@page
	import="de.tw.common.comparator.Comparator_Categories_BY_RANK_PARENT"%>
<%@page import="de.tw.common.comparator.Comparator_Common"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="de.mkoenig.taglib.FormatTag"%>
<%@page import="de.mkoenig.tools.performance.PerformanceMeasure"%>
<%@ taglib uri="/WEB-INF/tld/JSTL/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/JSTL/c-rt.tld" prefix="c_rt"%>




<head>
<title>
ZooPraxi.Beta - Tapeten | TapetenAgentur - im Online Shop kaufen
</title>
<link rel="canonical" href="http://www.tapetenagentur.de/Tapeten-603/EigenHeim-ZooPraxi-Beta.rf"/>
<meta name="google" content="notranslate"/>
<meta name="verify-v1" content="WoIlR8ng8wRbI9gMw7ZhTutYFWmxJB0crLFkVVqAhvY="/>
<meta name="google-site-verification" content="oe-cSFDVRlVO95Rq12uxybu8dqaA1DeBt6zwiWwh2vQ"/>
<link rel="shortcut icon" href="http://www.tapetenagentur.de/mb_ta/html/favicon.ico" type="image/x-icon"/>
<meta name="description" content="ZooPraxi.Beta  Tapeten - im Online Shop kaufen - Hier findest Du Deine  ZooPraxi.Beta  Tapete">
<meta name="keywords" content="products,tapeten,zoopraxi.what?Tapeten, Tapete , K� onlineshop">
<base href='http://www.tapetenagentur.de/mb_ta/'/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta name="robots" content="index,follow"/>
<meta property="fb:page_id" content="163905255207"/>
<meta property="fb:admins" content="1477549582"/>
<script type="text/javascript" src="http://static.tapetenagentur.de/mb_ta_war/res/js/_mb.js/mb.js"></script>
<script type="text/javascript" src="http://static.tapetenagentur.de/mb_ta_war/res/js/jquery-1.6.2.min.js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="http://static.tapetenagentur.de/mb_ta_war/res/js/jquery-ui-1.8.15/jquery-ui.min.1.8.15.js"></script>
<script type="text/javascript" src="http://static.tapetenagentur.de/mb_ta_war/res/js/jquery_form.js"></script>
<script type="text/javascript" src="http://static.tapetenagentur.de/mb_ta_war/res/js/jquery.form-defaults/jquery.form-defaults.js"></script>
<script type="text/javascript" src="http://static.tapetenagentur.de/mb_ta_war/res/js/jquery.mb.menu.2.8.5/inc/jquery.metadata.js"></script>
<script type="text/javascript" src="http://static.tapetenagentur.de/mb_ta_war/res/js/jquery.mb.menu.2.8.5/inc/jquery.hoverIntent.js"></script>
<script type="text/javascript" src="http://static.tapetenagentur.de/mb_ta_war/res/js/jquery.mb.menu.2.8.5/inc/mbMenu.js"></script>
<script type="text/javascript" src="http://static.tapetenagentur.de/mb_ta_war/res/js/jquery_tipTipv13/jquery.tipTip.js"></script>
<script src="http://static.tapetenagentur.de/mb_ta_war/res/js/selectmenu/ui.selectmenu.js" type="text/javascript"></script>
<script src="http://static.tapetenagentur.de/mb_ta_war/res/js/jquery.jqfancytransitions/jqFancyTransitions.1.8.js" type="text/javascript"></script>
<script src="http://static.tapetenagentur.de/mb_ta_war/res/js/magicthumb/magicthumb/magicthumb.js" type="text/javascript"></script>
<script src="http://static.tapetenagentur.de/mb_ta_war/res/js/SliderWall/SliderWall-Templates/sliderwall-bullets-1.1.2.js"></script>
<link rel="stylesheet" type="text/css" href='http://static.tapetenagentur.de/mb_ta_war/css/nexus_norm.css'/>
<link rel="stylesheet" type="text/css" href='http://static.tapetenagentur.de/mb_ta_war/css/nexus_default_shop.css'/>
<link rel="stylesheet" type="text/css" href='http://static.tapetenagentur.de/mb_ta_war/css/nexus_tapetenBorder.css'/>
<link rel="stylesheet" type="text/css" href='http://static.tapetenagentur.de/mb_ta_war/css/nexus_typo.css'/>
<link type="text/css" href="http://static.tapetenagentur.de/mb_ta_war//css/jquery/jquery-ui-1.8.14.custom/css/custom-theme/jquery-ui-1.8.14.custom.css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="http://static.tapetenagentur.de/mb_ta_war//css/menu_black.css" media="screen"/>
<link rel="stylesheet" type="text/css" href="http://static.tapetenagentur.de/mb_ta_war//css/tipTip.css" media="screen"/>
<link href="http://static.tapetenagentur.de/mb_ta_war/res/js/magicthumb/magicthumb/magicthumb.css" rel="stylesheet" type="text/css" media="screen"/>
<div id="waiting" style=visibility:hidden>
loading....<img src="res/img/ajax-loader.gif" alt="loading..."/>
</div>
<script src="http://www.google-analytics.com/urchin.js" type="text/javascript"></script>
<script type="text/javascript">_uacct="UA-515146-1";urchinTracker();</script>
<script type="text/javascript">var _gaq=_gaq||[];_gaq.push(['_setAccount','UA-515146-1']);_gaq.push(['_gat._anonymizeIp']);_gaq.push(['_trackPageview']);_gaq.push(['_trackPageLoadTime']);(function(){var ga=document.createElement('script');ga.type='text/javascript';ga.async=true;ga.src=('https:'==document.location.protocol?'https://ssl':'http://www')+'.google-analytics.com/ga.js';var s=document.getElementsByTagName('script')[0];s.parentNode.insertBefore(ga,s);})();</script>
</head>




<%
	I_NavigationHandler nav = (I_NavigationHandler) session
			.getAttribute(TWConsts.NAVIGATIONHANDLER);
	BusinessDelegate bd = BusinessDelegate.getInstance();
	
%>


<%-- CHECK JAVA.Installed --%>
<%-- CHECK JAVA.Installed --%>
<%-- CHECK JAVA.Installed --%>
<%-- CHECK JAVA.Installed --%>
<%-- CHECK JAVA.Installed --%>
<%-- CHECK JAVA.Installed --%>
<script language="javascript" type="text/javascript">

$(document).ready(function(){
	
	var browserInfo = navigator.userAgent;
	var jvmInstalled = 0;
	
	// variable to check if client is MSIE
	isIE = "false";
	//alert("browserInfo" + browserInfo );
	
	if(browserInfo.indexOf("MSIE")== -1 ){
	
		var index;
		var plugins=navigator.plugins;
		var plugin;
		for (index in plugins) {
			plugin=plugin + plugins[index].description  + "<br>";
			console.log("installed PlugIN:" + plugins[index].description );
		} 
		//alert("PlugIns:" + plugin);
		
		
		var mineTypes=navigator.mimeTypes;
		var erg;
		for (index in mineTypes) {
		    try {
			    erg=mineTypes[index].type  + "<br>";
			    //alert("supportMimeType:" + mineTypes[index].type);
			    //document.write(erg)
			   	console.log("support MimeType:" + mineTypes[index].type );
			    if(mineTypes[index].type.indexOf("JNLP") != -1 
			    		|| mineTypes[index].type.indexOf("jnlp") != -1
			    		|| mineTypes[index].type.indexOf("x-java-applet") != -1
			    	){
			    	jvmInstalled=1;
			    
			    	//break;
			    }
		    }
		    catch (e) {
        	//alert("error"+e );
        }
		} 
	
	
	}else{
		//alert("MSIE found");
		//var pluginObject = new ActiveXObject("java");
		// Create Java Web Start ActiveX object
		var x = new ActiveXObject('JavaWebStart.isInstalled');
		//alert("MSIE found" + x );
		try {
            var jws = new ActiveXObject('JavaWebStart.isInstalled');
            jvmInstalled=1;
            // alert("MSIE found JAVA" );
        }
        catch (e) {
        //	alert("MSIE found NO_JAVA" );
        }
		
	}
	
	//alert("XXX" + jvmInstalled);
	if(jvmInstalled==1){
	 	$("#div_JavaInstalled").append("<li>Java ab Version 1.6");
	}else{
		//alert("XXX");
	//	$("#div_JavaInstalled").append("<div class='h2' style='padding-top:20px;'><a href='http://www.java.com/de/download/' style='color:red;'>bitte installiere Java und rufe die Seite anschlieޥnd erneut auf...weiter</a></div>");
		//$("#zpStart").hide();
	}
	
	
	
});	
</script>


<table border="0">

	<%-- row 2 Navi...--%>
	<tr>
		<td>
			<table class="shadowWhite">
				<%--Banner 
				<div style="position: absolute; z-index: 1;">
					<img
						src="html/de/mb_templates/zoopraxi/zp_intro_img/new-ribbon.png"
						alt="New Ribbon">
				</div>
				--%>
				
				<%-- content... --%>
				<tr>
					<td valign="top">
					
						<div style="padding: 5px 5px 5px 5px">
							<%--oben, rechts,?,links --%>

						<%-- Anspann... --%>
							

							<%-- Slider... --%>
							<%-- Slider... --%>
							<%-- Slider... --%>	
							<div style="clear:left; padding-top: 20px; padding-bottom: 10px;">
							<jsp:include page="/html/de/mb_templates/zoopraxi/Zoopraxi_SlideShow.jsp"/>	
							</div>

							<%-- Anspann... --%>
							<%-- Anspann... --%>
							
							<%
							 String dic_LiebeZoopraxikanten_caption=bd.getMessage4Key("dic_LiebeZoopraxikanten_caption",nav.getLangCode(),"zoopraxiV2"); 							
							 String  dic_LiebeZoopraxikanten_text	=bd.getMessage4Key("dic_LiebeZoopraxikanten_text",nav.getLangCode(),"zoopraxiV2");
							%>
							
							<div style="padding-top: 0px; padding-bottom: 10px;">
								<%-- anspannText --%>
								<p class="h2" style="padding-bottom:25px;">
								<span style="font-weight: bold">
									<%=dic_LiebeZoopraxikanten_caption%>
								</span>
								</p>
								<p class="h3">
									<%=dic_LiebeZoopraxikanten_text%>
								</p>
							
							</div>


							<%-- start --%>
							<%-- start --%>
							<%-- start --%>
							<div style="padding-top: 10px;">
								<p class="h2">
									<span style="font-weight: bold">start</span>.Zoopraxi: 
								</p>
								<div style="padding-left: 20px; padding-top:10px;padding-right:150px; float:left;">	
									<%-- checkliste.. --%>
									<div class="h4"><bean:message key='dic_systemvoraussetzung' /></div>
								
								<div id="div_JavaInstalled" class="h4"></div>
								
									<div class="h4" style="padding-top: 15px;">
										<a href="http://www.java.com/de/download/testjava.jsp">Hier kannst Du Deine JavaVersion pr�der installieren? </a>
									</div>
								
								</div>
								<%-- start.. --%>
								<div id="zpStart" style="padding-left:50px; padding-top:10px; padding-bottom: 10px;">
										<img
										src="html/de/mb_templates/zoopraxi/zp_intro_img/start.png"
										width="175" height="31" alt="start" id="btn_startZoopraxi">
										
										<%--
											<% 	String textButton = BusinessDelegate.getInstance().getMessage4Key("los geht´s...", nav.getLangCode(), nav.getCurContext()); %>
											<html_mk:svgtext text='<%=textButton%>' font="verdana"
											fontsize="16" backgroundcolor="EFEFEF" textcolor="1f1f1f"
											strokecolor="EFEFEF" imagefolder="button_mb_ta" /> 
										--%>	
								</div>
										
							</div>
								
							<%-- 	Starter ...Scripting 
									*chromeBrowser startet als PlugIn
									*NonChrome als WebStart..
							--%>
							<%
							String linkStart = "http://www.tapetenagentur.de/mb_ta_war/html/de/mb_templates/zoopraxi/ZP_Prototyp_browser.jnlp";
							String linkStartChrome = "http://www.tapetenagentur.de/mb_ta/removeFilter/shop.do?command=go&category=45499";
							%>
							<script>
									$("#zpStart").click(function() { 
										//alert(navigator.javaEnabled());
										var userAgentX=navigator.userAgent;
										if( false
											//	userAgentX.indexOf('Chrome') != -1
											//alert("Chrome" +userAgentX 
											){		
											document.location.href="<%=linkStartChrome%>";
   									}else{
   										//alert("NoChrome" +userAgentX);	
   										document.location.href="<%=linkStart%>";
										}
									});
							</script>
							
						
							
							

						</div>
					</td>
				</tr>
				
				<%-- FACEBOOK --%>
				<%-- FACEBOOK --%>
				<%-- FACEBOOK --%>
				<%-- FACEBOOK --%>
				
				<tr>
				<td style="padding-left:100px; padding-top: 20px;">
				<table width=700 border=0><tr><td>
							<p class="h2">
								<span style="font-weight: bold">news</span>.Zoopraxi: 
							</p>
						
						<p>
							<div id="fb-root"></div>
							<script>(function(d, s, id) {
							  var js, fjs = d.getElementsByTagName(s)[0];
							  if (d.getElementById(id)) return;
							  js = d.createElement(s); js.id = id;
							  js.src = "//connect.facebook.net/de_DE/all.js#xfbml=1";
							  fjs.parentNode.insertBefore(js, fjs);
							}(document, 'script', 'facebook-jssdk'));</script>
							
							<div class="fb-like-box" data-href="http://www.facebook.com/zoopraxi" data-width="700" data-show-faces="false" data-border-color="#EFEFEF" data-stream="true" data-header="false"></div>
						</p>

						</td></tr>
						</table>
				</td>
				</tr>
				
				
			</table></td>
	</tr>

	<%-- row 2 Navi...--%>
	<tr>
		<td width="500px">
			<table>

				<tr>
					<td style="line-height: 5" align="center">&nbsp;</td>
				</tr>

				<tr>
					<td align="right"><a
						href="http://www.facebook.com/pages/ZooPraxi/357495797604972">-->
							ZooPraxi.facebook</a></td>
				</tr>

				<tr>
					<td align="right"><a href="https://twitter.com/#!/zoopraxi">-->
							ZooPraxi.Twitter</a></td>
				</tr>

				<tr>
					<td align="right"><a
						href="http://www.youtube.com/user/ZooPraxi">-->
							ZooPraxi.YouTube</a></td>
				</tr>


				<tr>
					<td align="right"><a
						href="http://www.tapetenagentur.de/mb_ta/removeFilter/shop.do?command=go&category=45499">-->
							ZooPraxi.BrowserPlugIn.Start</a></td>
				</tr>

				<tr>
					<td align="right"><a
						href="http://www.tapetenagentur.de/mb_ta_war/html/de/mb_templates/zoopraxi/ZP_Prototyp_browser.jnlp">-->
							ZooPraxi.WebStart</a></td>
				</tr>

<%--

				<tr>
					<td align="right"><a
						href="http://d18xo979gjlnj.cloudfront.net/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/ZP_Prototype2-1.0.exe">-->
							ZooPraxi.Version2 (windows 64Bit.exe)</a></td>
				</tr>

				<tr>
					<td align="right"><a
						href="http://d18xo979gjlnj.cloudfront.net/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/ZP_Prototype2-1.0.win586.exe">-->
							ZooPraxi.Version2 (windows 32Bit.exe)</a></td>
				</tr>
--%>
				
				<tr>
					<td align="right"><a
						href="http://www.tapetenagentur.de/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/inBrowser/ZP_Prototype2.html">-->
							ZooPraxi.Version2-applet</a></td>
				</tr>
				
				<tr>
					<td align="right"><a
						href="http://d18xo979gjlnj.cloudfront.net/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_64.exe">-->
							ZooPraxi.Version2 (windows 64Bit.exe)</a></td>
				</tr>

				
				<tr>
					<td align="right"><a
						href="http://d18xo979gjlnj.cloudfront.net/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_32_2_0_1.exe">-->
							ZooPraxi.Version2.0.1 (windows 32Bit.exe)</a></td>
				</tr>
				<tr>
					<td align="right"><a
						href="http://d18xo979gjlnj.cloudfront.net/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_32_2_0_2.exe">-->
							ZooPraxi.Version2.0.2 (windows 32Bit.exe)</a></td>
				</tr>
				
				<tr>
					<td align="right"><a
						href="http://d18xo979gjlnj.cloudfront.net/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_32_2_0_4.exe">-->
							ZooPraxi.Version2.0.4 (windows 32Bit.exe)-CDN</a></td>
				</tr>
				<tr>
					<td align="right"><a
						href="http://www.tapetenagentur.de/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_32_2_0_4.exe">-->
							ZooPraxi.Version2.0.4 (windows 32Bit.exe)</a></td>
				</tr>				


				<tr>
					<td align="right"><a
						href="http://d18xo979gjlnj.cloudfront.net/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_32_2_1_0.exe">-->
							ZooPraxi.Version2.1.0 (windows 32Bit.exe)-CDN</a></td>
				</tr>
				<tr>
					<td align="right"><a
						href="http://www.tapetenagentur.de/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_32_2_1_0.exe">-->
							ZooPraxi.Version2.1.0 (windows 32Bit.exe)</a></td>
				</tr>		

			<tr>
					<td align="right"><a
						href="http://d18xo979gjlnj.cloudfront.net/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_32_2_1_1.exe">-->
							ZooPraxi.Version2.1.1 (windows 32Bit.exe)-CDN</a></td>
				</tr>
				<tr>
					<td align="right"><a
						href="http://www.tapetenagentur.de/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_32_2_1_1.exe">-->
							ZooPraxi.Version2.1.1 (windows 32Bit.exe)</a></td>
				</tr>		


			<tr>
					<td align="right"><a
						href="http://d18xo979gjlnj.cloudfront.net/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_32_2_2_0.exe">-->
							ZooPraxi.Version2.2.0 (windows 32Bit.exe)-CDN</a></td>
				</tr>
				<tr>
					<td align="right"><a
						href="http://www.tapetenagentur.de/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_32_2_2_0.exe">-->
							ZooPraxi.Version2.2.0 (windows 32Bit.exe)</a></td>
				</tr>		
				

				<tr>
					<td align="right"><a
						href="http://d18xo979gjlnj.cloudfront.net/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_32_2_2_1.exe">-->
							ZooPraxi.Version2.2.1 (windows 32Bit.exe)-CDN</a></td>
				</tr>
				<tr>
					<td align="right"><a
						href="http://www.tapetenagentur.de/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_32_2_2_1.exe">-->
							ZooPraxi.Version2.2.1 (windows 32Bit.exe)</a></td>
				</tr>		
				
				<tr>
					<td align="right"><a
						href="http://d18xo979gjlnj.cloudfront.net/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_32_2_2_2.exe">-->
							ZooPraxi.Version2.2.2 (windows 32Bit.exe)-CDN</a></td>
				</tr>
				<tr>
					<td align="right"><a
						href="http://www.tapetenagentur.de/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_32_2_2_2.exe">-->
							ZooPraxi.Version2.2.2 (windows 32Bit.exe)</a></td>
				</tr>		
				
					<tr>
					<td align="right"><a
						href="http://www.tapetenagentur.de/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_64_2_2_3.exe">-->
							ZooPraxi.Version2.2.3 (windows 64Bit.exe)</a></td>
				</tr>		
				
				
								<tr>
					<td align="right"><a
						href="http://d18xo979gjlnj.cloudfront.net/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_32_2_2_4.exe">-->
							ZooPraxi.Version2.2.4 (windows 32Bit.exe)-CDN</a></td>
				</tr>
				<tr>
					<td align="right"><a
						href="http://www.tapetenagentur.de/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_32_2_2_4.exe">-->
							ZooPraxi.Version2.2.4 (windows 32Bit.exe)</a></td>
				</tr>	


				<tr>
					<td align="right"><a
						href="http://d18xo979gjlnj.cloudfront.net/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_32_2_3_0.exe">-->
							ZooPraxi.Version2.3.0 (windows 32Bit.exe)-CDN</a></td>
				</tr>
				<tr>
					<td align="right"><a
						href="http://www.tapetenagentur.de/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_32_2_3_0.exe">-->
							ZooPraxi.Version2.3.0 (windows 32Bit.exe)</a></td>
				</tr>			
				
		<tr>
					<td align="right"><a
						href="http://d18xo979gjlnj.cloudfront.net/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_32_2_3_1.exe">-->
							ZooPraxi.Version2.3.1 (windows 32Bit.exe)-CDN</a></td>
				</tr>
				<tr>
					<td align="right"><a
						href="http://www.tapetenagentur.de/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_32_2_3_1.exe">-->
							ZooPraxi.Version2.3.1 (windows 32Bit.exe)</a></td>
				</tr>			
				
	<tr>
					<td align="right"><a
						href="http://d18xo979gjlnj.cloudfront.net/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_32_2_3_2.exe">-->
							ZooPraxi.Version2.3.2 (windows 32Bit.exe)-CDN</a></td>
				</tr>
				<tr>
					<td align="right"><a
						href="http://www.tapetenagentur.de/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_32_2_3_2.exe">-->
							ZooPraxi.Version2.3.2 (windows 32Bit.exe)</a></td>
				</tr>			
				
				<!--2.3.7-->
				<tr>
					<td align="right"><a
						href="http://www.tapetenagentur.de/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_32_2_3_7.exe">-->
							ZooPraxi.Version2.3.7 (windows 32Bit.exe)</a>(Bem:load500/190DPI/hSlice8/)</td>
				</tr>			
				
<!--2.3.8-->
				<tr>
					<td align="right"><a
						href="http://www.tapetenagentur.de/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_32_2_3_8.exe">-->
							ZooPraxi.Version2.3.8 (windows 32Bit.exe)</a>(Bem:load_dyn/190DPI/hSlice?/)</td>
				</tr>			
				

<!--2.3.8-->
				<tr>
					<td align="right"><a
						href="http://www.tapetenagentur.de/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_32_2_3_8_2_1.exe">-->
							ZooPraxi.Version2.3.8.2.1 (windows 32Bit.exe)</a>(Bem:load_dyn/190DPI/hSlice?/)</td>
				</tr>			
				
	<!--2.3.8.2.3-->
				<tr>
					<td align="right"><a
						href="http://www.tapetenagentur.de/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_32_2_3_8_2_3.exe">-->
							ZooPraxi.Version2.3.8.2.3 (windows 32Bit.exe)</a>(Bem:load_dyn/190DPI/hSlice?/Server:ex10)
					</td>
				</tr>		
<!--2.3.8.2.5-->
				<tr>
					<td align="right"><a
						href="http://www.tapetenagentur.de/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_32_2_3_8_2_5.exe">-->
							ZooPraxi.Version2.3.8.2.5 (windows 32Bit.exe)</a>(Bem:load_dyn/190DPI/hSlice?/Server:ex10)
					</td>
				</tr>			
<!---END-->
<!--2.3.8.2.6-->
				<tr>
					<td align="right"><a
						href="http://www.tapetenagentur.de/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_32_2_3_8_2_6.exe">-->
							ZooPraxi.Version2.3.8.2.6 (windows 32Bit.exe)</a>(Bem:load_dyn/190DPI/hSlice?/Server:ex10)
					</td>
				</tr>			
<!---END-->
	
	<!--2.3.8.2.8.5-->
				<tr>
					<td align="right"><a
						href="http://www.tapetenagentur.de/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_32_2_3_8_2_8_5.exe">-->
							ZooPraxi.Version2.3.8.2.8.5 (windows 32Bit.exe)</a>(Bem:load_dyn/190DPI/hSlice?/Server:ex10)
					</td>
				</tr>			
<!---END-->

	<!--2.3.8.2.8.7-->
				<tr>
					<td align="right"><a
						href="http://www.tapetenagentur.de/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_32_2_3_8_2_8_7.exe">-->
							ZooPraxi.Version2.3.8.2.8.7 (windows 32Bit.exe)</a>(Bem:load_dyn/190DPI/hSlice?/Server:ex10)
					</td>
				</tr>			
<!---END-->

	<!--0.2.8.10-->
				<tr>
					<td align="right"><a
						href="http://www.tapetenagentur.de/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_32_0_2_8_10.exe">-->
						ZooPraxi.Version_0.2.8.10 (windows 32Bit.exe)</a>
					</td>
				</tr>			
<!---END-->
	<!--0.2.8.11-->
				<tr>
					<td align="right"><a
						href="http://www.tapetenagentur.de/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_32_0_2_8_11.exe">-->
						ZooPraxi.Version_0.2.8.11 (windows 32Bit.exe)</a>
					</td>
				</tr>			
<!---END-->
	
	<!--0.2.8.12-->
				<tr>
					<td align="right"><a
						href="http://www.tapetenagentur.de/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_32_0_2_8_12.exe">-->
						ZooPraxi.Version_0.2.8.12 (windows 32Bit.exe)</a>
					</td>
				</tr>			
<!---END-->
	<!--0.2.8.13-->
				<tr>
					<td align="right"><a
						href="http://www.tapetenagentur.de/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_32_0_2_8_13.exe">-->
						ZooPraxi.Version_0.2.8.13 (windows 32Bit.exe)</a>
					</td>
				</tr>			
<!---END-->
<!---END-->
	<!--0.2.8.14-->
				<tr>
					<td align="right"><a
						href="http://www.tapetenagentur.de/mb_ta_war/html/de/mb_templates/zoopraxi/zpV2/setup_zoopraxi_32_0_2_8_14.exe">-->
						ZooPraxi.Version_0.2.8.14 (windows 32Bit.exe)</a>
					</td>
				</tr>			
<!---END-->
	
				<tr>
					<td style="line-height: 5" align="center">&nbsp;</td>
				</tr>
			</table></td>
	</tr>


</table>

<%-- ARCHIVE--%>
<%-- ARCHIVE--%>
<%-- ARCHIVE--%>
<%-- ARCHIVE--%>
<%-- ARCHIVE--%>
<%-- ARCHIVE--%>
<%--
								<p class="h2">
									<span style="font-weight: bold">help</span>.Zoopraxi:
								</p>
								<p class="h4">kann klappen, muss aber nicht...und dann?</p>
							 
							 
								<div style="line-height: 15px; padding-top: 20px; padding-left:20px;">F�							Firefox:</div>
								<div class="h3" style="padding-left:20px; padding-top:5px;">
									Nach dem Klick auf <a href="<%=linkStart%>">"los geht´s..."</a>
									erscheint der folgende Dialog.. </br> </br> <img
										src="html/de/mb_templates/zoopraxi/zp_intro_img/jnlp_win7_firefox_.png">
									</br> </br> Sollte Dir keine Option "զfnen mit Java(TM)WebStartLauncher"
									angezeigt werden, musst Du zun㢨st noch Java installieren.
									Dabei hilft Dir dieser Link... <a
										href="http://www.java.com/de/download/"
										style="font-style: italic;">Java installieren.</a>. Rufe
									anschlieޥnd diese Seite erneut auf.
								</div>
							</div>
--%>

							<%-- video 
							<div style="padding-top: 20px; padding-bottom: 20px;">
								<p class="h2">
									<span style="font-weight: bold">help</span>.Zoopraxi.:
								</p>
								</br>
								<div style="float: left">
									<iframe width="200" height="165"
										src="http://www.youtube.com/embed/Iv9PxZ8j2_Q" frameborder="0"
										allowfullscreen></iframe>
									<iframe width="200" height="131"
										src="http://www.youtube.com/embed/ke7df0Wyl8w" frameborder="0"
										allowfullscreen></iframe>

								</div>

							</div>
							--%>