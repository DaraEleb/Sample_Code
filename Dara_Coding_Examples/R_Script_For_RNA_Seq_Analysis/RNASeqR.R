library("edgeR")

rawdata <- read.delim("RNASeq.txt", check.names=FALSE, stringsAsFactors=FALSE) #reads in the expression file 

head(rawdata) #to view the first couple sequences of the file to ensure it was read properly

group <- c("A","A","A","A","B","B","B","B") #groups the treatments of the experiment i.e. control vs MP

y <- DGEList(counts=rawdata[,2:9], group=group, genes=rawdata[,1:1]) #changes the file from text to matrix format

levels(y$samples$group) #outputs your groups 

y <- estimateCommonDisp(y)

y <- estimateTagwiseDisp(y) #estimates dispersion

et <- exactTest(y) #the test for differenctial expression, edgeR uses the quantile adjusted conditional maximum likelihood estimator

topTags(et) #shows the logFC, logCPM and pvalues

#TO PLOT MDS 

keep <- rowSums(cpm(y) > 0.5) >= 2 

plotMDS(y, col=colors[group], pch=pch[group])

#DONE

#TO PLOT HEATMAP

logCPM <- cpm(y)

logCPM <- t(scale(t(logCPM)))

library(gplots)

col.pan <- colorpanel(100, "blue", "white", "red")

heatmap.2(logCPM,col=col.pan,Rowv = TRUE, scale = "none", trace = "none",dendogram = "both",cexRow = 1, cexCol = 1.4, margin = c(10,9), lhei = c(2,10),lwid = c(2,6))

