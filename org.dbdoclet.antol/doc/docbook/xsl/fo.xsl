<?xml version ="1.0" encoding ="UTF-8"?>
<xsl:stylesheet exclude-result-prefixes="#default" version="1.0" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:import href="http://docbook.sourceforge.net/release/xsl/current/fo/docbook.xsl"></xsl:import>

  <xsl:param name='admon.graphics'>1</xsl:param>
  <xsl:param name='admon.graphics.extension'>.gif</xsl:param>
  <xsl:param name='admon.graphics.path'>/usr/share/dbdoclet/docbook/xsl/images/</xsl:param>
  <xsl:param name='alignment'>left</xsl:param>
  <xsl:param name='autotoc.label.separator'> </xsl:param>
  <xsl:param name='body.font.family'>serif</xsl:param>
  <xsl:param name='body.font.master'>10</xsl:param>
  <xsl:param name='chapter.autolabel'>1</xsl:param>
  <xsl:param name='column.count.back'>1</xsl:param>
  <xsl:param name='column.count.body'>1</xsl:param>
  <xsl:param name='column.count.front'>1</xsl:param>
  <xsl:param name='column.count.index'>1</xsl:param>
  <xsl:param name='double.sided'>0</xsl:param>
  <xsl:param name='draft.mode'>0</xsl:param>
  <xsl:param name="draft.watermark.image"></xsl:param>
  <xsl:param name='fop.extensions'>1</xsl:param>
  <xsl:param name='generate.index'>1</xsl:param>
  <xsl:param name='insert.xref.page.number'>1</xsl:param>
  <xsl:param name='page.orientation'>portrait</xsl:param>
  <xsl:param name='paper.type'>A4</xsl:param>
  <xsl:param name='section.autolabel'>0</xsl:param>
  <xsl:param name='section.label.includes.component.label'>0</xsl:param>
  <xsl:param name='shade.verbatim'>0</xsl:param>
  <xsl:param name='tablecolumns.extension'>0</xsl:param>
  <xsl:param name='title.margin.left'>0pt</xsl:param>
  <xsl:param name='toc.section.depth'>1</xsl:param>
  <xsl:param name='use.extensions'>1</xsl:param>

  <xsl:attribute-set name="section.title.properties">
    <xsl:attribute name="font-size">
      <xsl:value-of select="$body.font.master * 1.2"></xsl:value-of>
      <xsl:text>pt</xsl:text>
    </xsl:attribute>  
  </xsl:attribute-set>

  <xsl:attribute-set name="section.title.level1.properties">
    <xsl:attribute name="color">#444444</xsl:attribute>
    <xsl:attribute name="border-after-style">solid</xsl:attribute>
    <xsl:attribute name="border-after-width">.1mm</xsl:attribute>
    <xsl:attribute name="font-size">
      <xsl:value-of select="$body.font.master * 1.4"></xsl:value-of>
      <xsl:text>pt</xsl:text>
    </xsl:attribute>  
  </xsl:attribute-set> 

  <xsl:attribute-set name="section.title.level2.properties">
    <xsl:attribute name="color">#444444</xsl:attribute>
    <xsl:attribute name="padding">0.3em</xsl:attribute>
    <xsl:attribute name="start-indent">0pc</xsl:attribute>
    <xsl:attribute name="end-indent">0pc</xsl:attribute>
    <xsl:attribute name="font-style">italic</xsl:attribute>
    <xsl:attribute name="font-weight">normal</xsl:attribute>
    <xsl:attribute name="font-size">
      <xsl:value-of select="$body.font.master * 1.2"></xsl:value-of>
      <xsl:text>pt</xsl:text>
    </xsl:attribute>  
  </xsl:attribute-set> 

  <xsl:attribute-set name="section.title.level3.properties">
    <xsl:attribute name="color">#444444</xsl:attribute>
    <xsl:attribute name="border-after-style">solid</xsl:attribute>
    <xsl:attribute name="border-after-width">.1mm</xsl:attribute>
    <xsl:attribute name="font-weight">800</xsl:attribute>
    <xsl:attribute name="font-style">normal</xsl:attribute>
    <xsl:attribute name="font-size">
      <xsl:value-of select="$body.font.master * 1.0"></xsl:value-of>
      <xsl:text>pt</xsl:text>
    </xsl:attribute>  
    <xsl:attribute name="space-before.minimum">0.0em</xsl:attribute>
    <xsl:attribute name="space-before.optimum">2.0em</xsl:attribute>
    <xsl:attribute name="space-before.maximum">2.0em</xsl:attribute>
  </xsl:attribute-set> 

  <xsl:attribute-set name="formal.title.properties">

    <xsl:attribute name="font-size">
      <xsl:value-of select="$body.font.master * 1.0"></xsl:value-of>
      <xsl:text>pt</xsl:text>
    </xsl:attribute>

    <xsl:attribute name="space-after.minimum">0.0em</xsl:attribute>
    <xsl:attribute name="space-after.optimum">0.4em</xsl:attribute>
    <xsl:attribute name="space-after.maximum">0.4em</xsl:attribute>

    

  </xsl:attribute-set> 

  <xsl:attribute-set name="list.block.spacing">

    <xsl:attribute name="space-before.minimum">0em</xsl:attribute>
    <xsl:attribute name="space-before.optimum">0em</xsl:attribute>
    <xsl:attribute name="space-before.maximum">0em</xsl:attribute>

    <xsl:attribute name="space-after.minimum">0.0em</xsl:attribute>
    <xsl:attribute name="space-after.optimum">0.7em</xsl:attribute>
    <xsl:attribute name="space-after.maximum">0.7em</xsl:attribute>

    

  </xsl:attribute-set> 

  <xsl:attribute-set name="list.item.spacing">

    <xsl:attribute name="space-before.minimum">0.0em</xsl:attribute>
    <xsl:attribute name="space-before.optimum">0.3em</xsl:attribute>
    <xsl:attribute name="space-before.maximum">0.3em</xsl:attribute>

    <xsl:attribute name="space-after.minimum">0em</xsl:attribute>
    <xsl:attribute name="space-after.optimum">0.2em</xsl:attribute>
    <xsl:attribute name="space-after.maximum">0.2em</xsl:attribute>

    

  </xsl:attribute-set> 

  <xsl:template match="processing-instruction('line-break')">
    <fo:block></fo:block>
  </xsl:template>

</xsl:stylesheet>
