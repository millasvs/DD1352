<?php

function makeNiceGraph($file, $newname, $removeCount)
{
	$lines = explode("\n", file_get_contents($file));
	
	for ($i = 0; $i < $removeCount; $i++)
	{
		array_shift($lines);
	}
	
	$fileContent = "digraph\n";
	$fileContent .= "{\n";
	
	foreach ($lines as $line)
	{
		if (strlen($line) == 0) { continue; }
		
		$line = explode(" ", $line);
		
		$fileContent .= "\t" . $line[0] . ' -> ' . $line[1] . ' [label = ' . $line[2] . ']';
		$fileContent .= "\n";
	}
	
	$fileContent .= "}\n";
	
	file_put_contents($newname . '.dot', $fileContent);
	shell_exec('dot -Tpdf -o ' . $newname . '.pdf ' . $newname . '.dot');
}

makeNiceGraph('/home/martin/KTH/Kurser/Algoritmer, datastrukturer och komplexitet (DD1352)/labb3/del2/tmp/indata.txt', 'indata', 3);
makeNiceGraph('/home/martin/KTH/Kurser/Algoritmer, datastrukturer och komplexitet (DD1352)/labb3/del2/tmp/facit.txt', 'facit', 3);
makeNiceGraph('/home/martin/KTH/Kurser/Algoritmer, datastrukturer och komplexitet (DD1352)/labb3/del2/tmp/ours.txt', 'ours', 3);

?>